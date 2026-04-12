package ai.agentscentral.http.runner;

/**
 * Lifecycle interface for an AgentJ HTTP server.
 *
 * <p>Implementations are responsible for starting and stopping the underlying server
 * (e.g. Jetty).  The canonical implementation lives in {@code agentj-jetty}.
 * Use {@link AgentJStarter#run(AgentJHttpRunner)} as the application entry point — it
 * prints the startup banner and then calls {@link #start()}.</p>
 *
 * @author Rizwan Idrees
 */
public interface AgentJHttpRunner {

    /**
     * Starts the HTTP server and begins accepting requests.
     *
     * @throws Exception if the server fails to start
     */
    void start() throws Exception;

    /**
     * Gracefully shuts down the HTTP server.
     *
     * @throws Exception if the server fails to stop cleanly
     */
    void stop() throws Exception;

}
