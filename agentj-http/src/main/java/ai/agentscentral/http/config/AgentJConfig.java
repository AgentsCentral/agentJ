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
 * Root configuration record for an AgentJ HTTP server.
 *
 * <p>Holds the complete list of {@link Route}s to register with the servlet.
 * Instances are constructed via the fluent {@link AgentJConfigBuilder} obtained from
 * {@link #agentJConfig()}.  The builder pre-registers default liveness
 * ({@code /liveness}) and readiness ({@code /readiness}) health-check routes and
 * converts each declared agentic route into an {@link HttpHandlerRoute} backed by an
 * {@link AgenticHttpHandler}.</p>
 *
 * @param routes the complete ordered list of {@link Route}s served by this configuration
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

    /**
     * Returns a new {@link AgentJConfigBuilder} for fluent configuration.
     *
     * @return a fresh builder pre-populated with default health-check routes
     */
    public static AgentJConfigBuilder agentJConfig() {
        return new AgentJConfigBuilder();
    }

    /**
     * Fluent builder for {@link AgentJConfig}.
     *
     * <p>Default liveness and readiness probes are registered at {@code /liveness} and
     * {@code /readiness} and can be overridden via the corresponding setter methods.
     * Agentic routes are declared with {@link #agenticRoute} and are converted to
     * {@link HttpHandlerRoute}s backed by {@link AgenticHttpHandler} during
     * {@link #build()}.</p>
     */
    public static class AgentJConfigBuilder {

        private AgenticModule agenticModule = AgenticModule.defaultAgenticModule();
        private Route livenessRoute = DEFAULT_LIVENESS_ROUTE;
        private Route readinesRoute = DEFAULT_READINESS_ROUTE;
        private final List<AgenticRouteInternal> agenticRoutes = new ArrayList<>();
        private final List<Route> routes = new ArrayList<>();

        /**
         * Builds the {@link AgentJConfig}, finalising all routes.
         *
         * @return the assembled configuration
         */
        public AgentJConfig build() {
            routes.add(livenessRoute);
            routes.add(readinesRoute);

            agenticRoutes.forEach(ar -> routes.add(toHttpHandlerRoute(ar)));

            return new AgentJConfig(routes);
        }

        /**
         * Overrides the default {@link AgenticModule} used when building agentic routes.
         *
         * @param agenticModule the module to use; must not be {@code null}
         * @return this builder
         */
        public AgentJConfigBuilder agenticModule(AgenticModule agenticModule){
            this.agenticModule = agenticModule;
            return this;
        }

        /**
         * Overrides the liveness probe path, keeping the default no-op probe handler.
         *
         * @param path the URL path for the liveness endpoint
         * @return this builder
         */
        public AgentJConfigBuilder livenessRoute(String path) {
            this.livenessRoute = new HttpHandlerRoute<>(path, GET, DEFAULT_PROBE_HANDLER);
            return this;
        }

        /**
         * Overrides the liveness probe path and handler.
         *
         * @param path    the URL path for the liveness endpoint
         * @param handler the custom {@link HttpHandler} to serve liveness responses
         * @return this builder
         */
        public AgentJConfigBuilder livenessRoute(String path, HttpHandler<ProbeResponse> handler) {
            this.livenessRoute = new HttpHandlerRoute<>(path, GET, handler);
            return this;
        }

        /**
         * Overrides the readiness probe path, keeping the default no-op probe handler.
         *
         * @param path the URL path for the readiness endpoint
         * @return this builder
         */
        public AgentJConfigBuilder readinessRoute(String path) {
            this.readinesRoute = new HttpHandlerRoute<>(path, GET, DEFAULT_PROBE_HANDLER);
            return this;
        }

        /**
         * Overrides the readiness probe path and handler.
         *
         * @param path    the URL path for the readiness endpoint
         * @param handler the custom {@link HttpHandler} to serve readiness responses
         * @return this builder
         */
        public AgentJConfigBuilder readinessRoute(String path, HttpHandler<ProbeResponse> handler) {
            this.readinesRoute = new HttpHandlerRoute<>(path, GET, handler);
            return this;
        }

        /**
         * Registers an {@link Agentic} (agent or team) at the given POST path.
         * The route is backed by an {@link AgenticHttpHandler} wired with the current
         * {@link AgenticModule}.
         *
         * @param path    the URL path for this agentic endpoint
         * @param agentic the agent or team to serve at this path
         * @return this builder
         */
        public AgentJConfigBuilder agenticRoute(String path, Agentic agentic) {
            agenticRoutes.add(new AgenticRouteInternal(path, agentic));
            return this;
        }

        /**
         * Registers a custom {@link HttpHandler} at the given path and HTTP method.
         *
         * @param path    the URL path for this route
         * @param method  the HTTP method to match
         * @param handler the handler to invoke on match
         * @return this builder
         */
        public AgentJConfigBuilder httpRoute(String path,
                                             HttpMethod method,
                                             HttpHandler<?> handler) {
            routes.add(new HttpHandlerRoute<>(path, method, handler));
            return this;
        }

        /**
         * Registers a controller object whose methods are discovered via
         * {@link ai.agentscentral.http.route.annotations.Get},
         * {@link ai.agentscentral.http.route.annotations.Post}, etc. annotations.
         *
         * @param path       the base URL path prefix for all methods on this controller
         * @param controller the controller instance to reflect over
         * @return this builder
         */
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
