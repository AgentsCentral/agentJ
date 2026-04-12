package ai.agentscentral.jetty.config;

/**
 * Immutable configuration record for the Jetty HTTP server.
 *
 * <p>Use {@link #defaultConfig()} for a zero-config server bound to
 * {@value #DEFAULT_HOST}:{@value #DEFAULT_PORT}, or {@link #builder()} to override
 * individual settings:
 * <pre>{@code
 * JettyConfig config = JettyConfig.builder()
 *         .host("127.0.0.1")
 *         .port(9090)
 *         .maxVirtualThreads(256)
 *         .build();
 * }</pre>
 *
 * @param host              the network interface address the server binds to
 * @param port              the TCP port the server listens on
 * @param maxVirtualThreads maximum number of virtual threads in the
 *                          {@link org.eclipse.jetty.util.thread.VirtualThreadPool}
 * @param reservedThreads   number of threads reserved in the
 *                          {@link org.eclipse.jetty.util.thread.QueuedThreadPool}
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
    /** Default bind address ({@code 0.0.0.0} — all interfaces). */
    public static final String DEFAULT_HOST = "0.0.0.0";

    /** Default server port ({@code 8181}). */
    public static final int DEFAULT_PORT = 8181;

    /** Default maximum virtual-thread count ({@code 128}). */
    public static final int DEFAULT_MAX_VIRTUAL_THREADS = 128;

    /** Default number of reserved threads ({@code 2}). */
    public static final int DEFAULT_RESERVED_THREADS = 2;

    /**
     * Returns a {@code JettyConfig} populated with all default values.
     *
     * @return a default {@code JettyConfig}
     */
    public static JettyConfig defaultConfig() {
        return builder().build();
    }

    /**
     * Returns a new {@link JettyConfigBuilder} pre-populated with all default values.
     *
     * @return a new builder
     */
    public static JettyConfigBuilder builder() {
        return new JettyConfigBuilder();
    }

    /**
     * Fluent builder for {@link JettyConfig}.
     */
    public static class JettyConfigBuilder {
        private String host = DEFAULT_HOST;
        private int port = DEFAULT_PORT;
        private int maxVirtualThreads = DEFAULT_MAX_VIRTUAL_THREADS;
        private int reservedThreads = DEFAULT_RESERVED_THREADS;

        /**
         * Sets the bind address.
         *
         * @param host network interface address (e.g. {@code "0.0.0.0"} or
         *             {@code "127.0.0.1"})
         * @return this builder
         */
        public JettyConfigBuilder host(String host) {
            this.host = host;
            return this;
        }

        /**
         * Sets the TCP port the server listens on.
         *
         * @param port the port number
         * @return this builder
         */
        public JettyConfigBuilder port(int port) {
            this.port = port;
            return this;
        }

        /**
         * Sets the maximum number of virtual threads.
         *
         * @param maxVirtualThreads maximum virtual thread count
         * @return this builder
         */
        public JettyConfigBuilder maxVirtualThreads(int maxVirtualThreads) {
            this.maxVirtualThreads = maxVirtualThreads;
            return this;
        }

        /**
         * Sets the number of reserved threads in the queued thread pool.
         *
         * @param reservedThreads reserved thread count
         * @return this builder
         */
        public JettyConfigBuilder reservedThreads(int reservedThreads) {
            this.reservedThreads = reservedThreads;
            return this;
        }

        /**
         * Builds the {@link JettyConfig}.
         *
         * @return the assembled configuration
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
