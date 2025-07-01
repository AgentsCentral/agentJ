package ai.agentscentral.http.runner;

import static ai.agentscentral.http.banner.Banner.printBanner;

/**
 * AgentJStarter
 *
 * @author Rizwan Idrees
 */
public class AgentJStarter {
    private AgentJStarter() {
    }

    public static void run(AgentJHttpRunner runner) throws Exception {
        printBanner();
        runner.start();
    }

}
