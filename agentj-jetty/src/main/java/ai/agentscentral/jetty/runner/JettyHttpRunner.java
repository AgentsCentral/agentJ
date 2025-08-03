package ai.agentscentral.jetty.runner;

import ai.agentscentral.core.context.InMemoryContextManager;
import ai.agentscentral.core.context.InMemoryContextStateManager;
import ai.agentscentral.core.factory.AgentJFactory;
import ai.agentscentral.core.factory.DefaultAgentJFactory;
import ai.agentscentral.core.session.processor.DefaultSessionProcessor;
import ai.agentscentral.http.config.AgentJConfig;
import ai.agentscentral.http.config.HttpConfig;
import ai.agentscentral.http.filter.AgentJAuthorizationFilter;
import ai.agentscentral.http.health.LivenessProbe;
import ai.agentscentral.http.health.ReadinessProbe;
import ai.agentscentral.http.request.JsonRequestExtractor;
import ai.agentscentral.http.request.TrailingRequestPathSessionIdExtractor;
import ai.agentscentral.http.response.JsonResponseSender;
import ai.agentscentral.http.runner.AgentJHttpRunner;
import ai.agentscentral.http.servlet.AgentJServlet;
import ai.agentscentral.http.servlet.HealthCheckServlet;
import ai.agentscentral.jetty.config.JettyConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.DispatcherType;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.VirtualThreadPool;

import java.util.EnumSet;
import java.util.concurrent.Executors;

import static ai.agentscentral.core.session.config.ExecutionLimits.defaultExecutionLimits;
import static java.util.Objects.nonNull;

/**
 * JettyHttpRunner
 * Jetty implementation of AgentJHttpRunner
 *
 * @author Rizwan Idrees
 */
public class JettyHttpRunner implements AgentJHttpRunner {

    private final JettyConfig jettyConfig;
    private final AgentJConfig agentJConfig;
    private final Server server;

    public JettyHttpRunner(JettyConfig jettyConfig, AgentJConfig agentJConfig) {
        this.jettyConfig = jettyConfig;
        this.agentJConfig = agentJConfig;
        this.server = new Server(queuedThreadPool());
    }

    @Override
    public void start() throws Exception {

        final HttpConnectionFactory httpConnectionFactory = new HttpConnectionFactory(new HttpConfiguration());
        final ServletContextHandler servletContextHandler = new ServletContextHandler();

        addHttpAgentSystems(servletContextHandler);
        addHealthChecks(servletContextHandler);

        server.setHandler(servletContextHandler);


        try (ServerConnector connector = new ServerConnector(server, httpConnectionFactory)) {
            connector.setPort(jettyConfig.port());
            server.addConnector(connector);
        }

        server.setStopAtShutdown(true);
        server.start();
    }

    private QueuedThreadPool queuedThreadPool() {
        final QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setVirtualThreadsExecutor(Executors.newVirtualThreadPerTaskExecutor());
        VirtualThreadPool virtualExecutor = new VirtualThreadPool();
        virtualExecutor.setMaxThreads(jettyConfig.maxVirtualThreads());
        threadPool.setVirtualThreadsExecutor(virtualExecutor);
        threadPool.setReservedThreads(jettyConfig.reservedThreads());
        return threadPool;
    }

    private void addHealthChecks(ServletContextHandler servletContextHandler) {
        final LivenessProbe livenessProbe = new LivenessProbe() {
        };
        final ReadinessProbe readinessProbe = new ReadinessProbe() {
        };
        servletContextHandler.addServlet(new HealthCheckServlet(livenessProbe, readinessProbe), "/health/*");
    }


    private void addHttpAgentSystems(ServletContextHandler servletContextHandler) {

        for (HttpConfig httpConfig : agentJConfig.httpConfigs()) {
            AgentJFactory agentJFactory = DefaultAgentJFactory.getInstance();
            final DefaultSessionProcessor processor = new DefaultSessionProcessor(httpConfig.agentic(),
                    agentJFactory,
                    new InMemoryContextStateManager(),
                    new InMemoryContextManager(), defaultExecutionLimits());

            final ObjectMapper objectMapper = new ObjectMapper(); //TODO use singleton object mapper
            final AgentJServlet servlet = new AgentJServlet(processor,
                    new JsonRequestExtractor(objectMapper),
                    new JsonResponseSender(objectMapper),
                    new TrailingRequestPathSessionIdExtractor(httpConfig.path()),
                    agentJFactory.getSessionIdGenerator(),
                    agentJFactory.getMessageIdGenerator());

            servletContextHandler.addServlet(servlet, httpConfig.path());

            if (nonNull(httpConfig.authorizers()) && !httpConfig.authorizers().isEmpty()) {
                servletContextHandler.addFilter(new AgentJAuthorizationFilter(httpConfig.authorizers()),
                        httpConfig.path(), EnumSet.of(DispatcherType.REQUEST));
            }
        }
    }

    @Override
    public void stop() throws Exception {

        if (nonNull(server)) {
            server.stop();
        }
    }
}
