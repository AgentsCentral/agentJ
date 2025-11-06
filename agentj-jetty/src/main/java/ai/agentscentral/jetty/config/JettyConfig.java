package ai.agentscentral.jetty.config;

/**
 * Configuration class for Jetty server settings.
 *
 * @param host the server host address
 * @param port the server port number
 * @param maxVirtualThreads maximum number of virtual threads
 * @param reservedThreads number of reserved threads
 *
 * @author Rizwan Idrees
 * @author Mustafa Bhuiyan
 */
public record JettyConfig(
        String host,
        int port,
        int maxVirtualThreads,
        int reservedThreads
) {
    public static final String DEFAULT_HOST = "0.0.0.0";
    public static final int DEFAULT_PORT = 8181;
    public static final int DEFAULT_MAX_VIRTUAL_THREADS = 128;
    public static final int DEFAULT_RESERVED_THREADS = 2;

    /**
     * Creates a new JettyConfig with default values.
     * @return a new JettyConfig instance with default values
     */
    public static JettyConfig defaultConfig() {
        return builder().build();
    }

    /**
     * Creates a new builder with default values.
     * @return a new JettyConfigBuilder with default values
     */
    public static JettyConfigBuilder builder() {
        return new JettyConfigBuilder();
    }

    public static class JettyConfigBuilder {
        private String host = DEFAULT_HOST;
        private int port = DEFAULT_PORT;
        private int maxVirtualThreads = DEFAULT_MAX_VIRTUAL_THREADS;
        private int reservedThreads = DEFAULT_RESERVED_THREADS;

        public JettyConfigBuilder host(String host) {
            this.host = host;
            return this;
        }
        public JettyConfigBuilder port(int port) {
            this.port = port;
            return this;
        }

        public JettyConfigBuilder maxVirtualThreads(int maxVirtualThreads) {
            this.maxVirtualThreads = maxVirtualThreads;
            return this;
        }

        public JettyConfigBuilder reservedThreads(int reservedThreads) {
            this.reservedThreads = reservedThreads;
            return this;
        }

        /**
         * Builds the JettyConfig instance with validation.
         * 
         * @return a new JettyConfig instance
         */
        public JettyConfig build() {
            return new JettyConfig(
                    host,
                    port,
                    maxVirtualThreads,
                    reservedThreads
            );
        }
    }
}
