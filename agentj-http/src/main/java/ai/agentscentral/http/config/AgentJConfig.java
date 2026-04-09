package ai.agentscentral.http.config;

import ai.agentscentral.core.agentic.Agentic;
import ai.agentscentral.core.agentic.AgenticModule;
import ai.agentscentral.http.handler.AgenticHttpHandler;
import ai.agentscentral.http.handler.HttpHandler;
import ai.agentscentral.http.handler.ProbeHandler;
import ai.agentscentral.http.health.Probe;
import ai.agentscentral.http.health.ProbeResponse;
import ai.agentscentral.http.response.MessageResponse;
import ai.agentscentral.http.route.ControllerRoute;
import ai.agentscentral.http.route.HttpHandlerRoute;
import ai.agentscentral.http.route.HttpMethod;
import ai.agentscentral.http.route.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ai.agentscentral.http.route.HttpMethod.GET;
import static ai.agentscentral.http.route.HttpMethod.POST;

/**
 * AgentJConfig
 *
 * @author Rizwan Idrees
 */
public record AgentJConfig(List<Route> routes) {

    private static final Probe defaultProbe = request -> new ProbeResponse(200, Map.of());
    private static final HttpHandler<ProbeResponse> DEFAULT_PROBE_HANDLER = new ProbeHandler(defaultProbe);
    private static final HttpHandlerRoute<ProbeResponse> DEFAULT_LIVENESS_ROUTE = new HttpHandlerRoute<>("/liveness", GET,
            DEFAULT_PROBE_HANDLER);
    private static final HttpHandlerRoute<ProbeResponse> DEFAULT_READINESS_ROUTE = new HttpHandlerRoute<>("/readiness", GET,
            DEFAULT_PROBE_HANDLER);

    public static AgentJConfigBuilder agentJConfig() {
        return new AgentJConfigBuilder();
    }

    public static class AgentJConfigBuilder {

        private AgenticModule agenticModule = AgenticModule.defaultAgenticModule();
        private Route livenessRoute = DEFAULT_LIVENESS_ROUTE;
        private Route readinesRoute = DEFAULT_READINESS_ROUTE;
        private final List<AgenticRouteInternal> agenticRoutes = new ArrayList<>();
        private final List<Route> routes = new ArrayList<>();

        public AgentJConfig build() {
            routes.add(livenessRoute);
            routes.add(readinesRoute);

            agenticRoutes.forEach(ar -> routes.add(toHttpHandlerRoute(ar)));

            return new AgentJConfig(routes);
        }

        public AgentJConfigBuilder agenticModule(AgenticModule agenticModule){
            this.agenticModule = agenticModule;
            return this;
        }

        public AgentJConfigBuilder livenessRoute(String path) {
            this.livenessRoute = new HttpHandlerRoute<>(path, GET, DEFAULT_PROBE_HANDLER);
            return this;
        }

        public AgentJConfigBuilder livenessRoute(String path, HttpHandler<ProbeResponse> handler) {
            this.livenessRoute = new HttpHandlerRoute<>(path, GET, handler);
            return this;
        }

        public AgentJConfigBuilder readinessRoute(String path) {
            this.readinesRoute = new HttpHandlerRoute<>(path, GET, DEFAULT_PROBE_HANDLER);
            return this;
        }

        public AgentJConfigBuilder readinessRoute(String path, HttpHandler<ProbeResponse> handler) {
            this.readinesRoute = new HttpHandlerRoute<>(path, GET, handler);
            return this;
        }

        public AgentJConfigBuilder agenticRoute(String path, Agentic agentic) {
            agenticRoutes.add(new AgenticRouteInternal(path, agentic));
            return this;
        }

        public AgentJConfigBuilder httpRoute(String path,
                                             HttpMethod method,
                                             HttpHandler<?> handler) {
            routes.add(new HttpHandlerRoute<>(path, method, handler));
            return this;
        }

        public AgentJConfigBuilder controllerRoute(String path,
                                                   Object controller) {
            routes.add(new ControllerRoute(path, controller));
            return this;
        }

        private HttpHandlerRoute<MessageResponse> toHttpHandlerRoute(AgenticRouteInternal internal){
            final AgenticConfig agenticConfig = AgenticConfig.builder()
                    .defaultConfig(internal.agentic, agenticModule).build();
            return new HttpHandlerRoute<>(internal.path(), POST, new AgenticHttpHandler(agenticConfig));
        }

    }

    private record AgenticRouteInternal(String path, Agentic agentic){}

}
