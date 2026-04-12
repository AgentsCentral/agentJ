package ai.agentscentral.http.runner;

import static ai.agentscentral.http.banner.Banner.printBanner;

/**
 * Application entry-point utility for starting an AgentJ HTTP server.
 *
 * <p>Prints the ASCII art startup banner and then delegates to
 * {@link AgentJHttpRunner#start()}.  Typical usage:</p>
 * <pre>
 * AgentJStarter.run(new JettyHttpRunner(agentJConfig, httpConfig));
 * </pre>
 *
 * <p>This is a utility class and cannot be instantiated.</p>
 *
 * @author Rizwan Idrees
 */
public class AgentJStarter {
    private AgentJStarter() {
    }

    /**
     * Prints the startup banner and starts the given runner.
     *
     * @param runner the HTTP server runner to start
     * @throws Exception if the runner fails to start
     */
    public static void run(AgentJHttpRunner runner) throws Exception {
        printBanner();
        runner.start();
    }

}
