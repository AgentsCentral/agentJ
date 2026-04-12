package ai.agentscentral.jetty.runner;

import ai.agentscentral.http.config.AgentJConfig;
import ai.agentscentral.http.route.convertors.DefaultContentConvertor;
import ai.agentscentral.http.runner.AgentJHttpRunner;
import ai.agentscentral.http.servlet.AgentJServlet;
import ai.agentscentral.jetty.config.JettyConfig;
import jakarta.annotation.Nonnull;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.VirtualThreadPool;

import java.util.Objects;
import java.util.concurrent.Executors;

import static java.util.Objects.nonNull;

/**
 * Jetty 12 implementation of {@link AgentJHttpRunner}.
 *
 * <p>Starts a Jetty {@link Server} backed by a virtual-thread pool and registers the
 * {@link AgentJServlet} to handle all inbound requests under {@code /**}.  The server
 * is configured to stop gracefully on JVM shutdown.</p>
 *
 * <p>Typical usage:
 * <pre>{@code
 * AgentJHttpRunner runner = new JettyHttpRunner(
 *         JettyConfig.defaultConfig(),
 *         agentJConfig);
 * runner.start();
 * }</pre>
 *
 * @author Rizwan Idrees
 * @author Mustafa Bhuiyan
 */
public class JettyHttpRunner implements AgentJHttpRunner {

    private final JettyConfig jettyConfig;
    private final AgentJConfig agentJConfig;
    private final Server server;

    /**
     * Creates a {@code JettyHttpRunner} with the given server and application
     * configuration.
     *
     * @param jettyConfig  Jetty-specific settings (host, port, thread pool); must not be
     *                     {@code null}
     * @param agentJConfig AgentJ application configuration (routes, handlers, filters);
     *                     must not be {@code null}
     * @throws NullPointerException if either argument is {@code null}
     */
    public JettyHttpRunner(@Nonnull JettyConfig jettyConfig, @Nonnull AgentJConfig agentJConfig) {
        this.jettyConfig = Objects.requireNonNull(jettyConfig, "JettyConfig cannot be null");
        this.agentJConfig = Objects.requireNonNull(agentJConfig, "AgentJConfig cannot be null");
        this.server = new Server(queuedThreadPool());
    }

    @Override
    public void start() throws Exception {

        final HttpConnectionFactory httpConnectionFactory = new HttpConnectionFactory(new HttpConfiguration());
        final ServletContextHandler servletContextHandler = new ServletContextHandler();

        addHttpSystems(servletContextHandler);

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
        virtualExecutor.setMaxConcurrentTasks(jettyConfig.maxVirtualThreads());
        threadPool.setVirtualThreadsExecutor(virtualExecutor);
        threadPool.setReservedThreads(jettyConfig.reservedThreads());
        return threadPool;
    }

    private void addHttpSystems(ServletContextHandler servletContextHandler) {
        final AgentJServlet servlet = new AgentJServlet(agentJConfig.routes(), new DefaultContentConvertor());
        servletContextHandler.addServlet(servlet, "/**");
    }

    @Override
    public void stop() throws Exception {
        if (nonNull(server)) {
            server.stop();
        }
    }
}
