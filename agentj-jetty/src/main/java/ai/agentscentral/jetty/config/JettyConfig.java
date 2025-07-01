package ai.agentscentral.jetty.config;

/**
 * JettyConfig
 *
 * @param port
 * @param maxVirtualThreads
 * @param reservedThreads
 * @author Rizwan Idrees
 */
public record JettyConfig(int port, int maxVirtualThreads, int reservedThreads) {

    public static JettyConfig defaultJettyConfig() {
        return new JettyConfig(8181, 128, 2);
    }

}
