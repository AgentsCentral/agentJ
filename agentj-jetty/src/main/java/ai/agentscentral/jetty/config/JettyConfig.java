package ai.agentscentral.jetty.config;

import java.util.Set;

/**
 * Configuration class for Jetty server settings.
 *
 * @param host the server host address
 * @param port the server port number
 * @param maxVirtualThreads maximum number of virtual threads
 * @param reservedThreads number of reserved threads
 * @param corsEnabled whether CORS is enabled
 * @param allowedOrigins set of allowed origins for CORS
 * @param allowedHeaders set of allowed headers for CORS
 * @param allowedMethods set of allowed HTTP methods for CORS
 * @param allowCredentials whether to allow credentials in CORS requests
 * @author Rizwan Idrees
 * @author Mustafa Bhuiyan
 */
public record JettyConfig(
        String host,
        int port,
        int maxVirtualThreads,
        int reservedThreads,
        boolean corsEnabled,
        Set<String> allowedOrigins,
        Set<String> allowedHeaders,
        Set<String> allowedMethods,
        boolean allowCredentials
) {
    // Default config values
    public static final String DEFAULT_HOST = "0.0.0.0";
    public static final int DEFAULT_PORT = 8181;
    public static final int DEFAULT_MAX_VIRTUAL_THREADS = 128;
    public static final int DEFAULT_RESERVED_THREADS = 2;

    /**
      Default CORS configuration values.

      @SECURITY_WARNING: The default CORS configuration is intentionally restrictive.
     *          In production, explicitly configure CORS to allow only trusted origins.
     *          Using "*" as an allowed origin is not recommended for production
     *          as it allows any website to make cross-origin requests to your API.
     */

    /**
     * Default CORS enabled state (false).
     * CORS is disabled by default for security reasons.
     */
    public static final boolean DEFAULT_CORS_ENABLED = false;
    public static final boolean DEFAULT_ALLOW_CREDENTIALS = false;
    public static final Set<String> DEFAULT_ALLOWED_ORIGINS = Set.of(); // Empty set by default - must be explicitly configured
    public static final Set<String> DEFAULT_ALLOWED_HEADERS = Set.of("Content-Type", "Authorization");
    public static final Set<String> DEFAULT_ALLOWED_METHODS = Set.of("GET", "HEAD", "OPTIONS");

    /**
     * Creates a new JettyConfig with default values.
     * Note: CORS is disabled by default. Use withCors() to enable it.
     * @return a new JettyConfig instance with default values
     */
    public static JettyConfig defaultConfig() {
        return builder()
                .corsEnabled(false)
                .build();
    }

    /**
     * Creates a new builder with default values.
     * @return a new JettyConfigBuilder with default values
     */
    public static JettyConfigBuilder builder() {
        return new JettyConfigBuilder();
    }

    /**
     * Creates a new builder with CORS enabled and default CORS settings.
     *
     * @return a new JettyConfigBuilder with CORS enabled
     * @throws IllegalStateException if no origins are configured when building
     * <p><b>Note:</b> SECURITY WARNING: This enables CORS with minimal restrictions by default.
     * In production, explicitly set allowedOrigins to specific trusted domains.
     */
    public static JettyConfigBuilder withCors() {
        return builder().corsEnabled(true);
    }
    
    /**
     * Creates a new builder with CORS enabled for specific origins, using default headers and methods.
     * This is the recommended way to enable CORS with secure defaults.
     *
     * @param allowedOrigins Set of allowed origin domains (e.g., "https://example.com")
     * @return a configured JettyConfigBuilder with CORS enabled
     * @throws IllegalArgumentException if allowedOrigins is null or empty
     * <p><b>Default headers:</b> "Content-Type", "Authorization"
     * <p><b>Default methods:</b> "GET", "HEAD", "OPTIONS"
     */
    public static JettyConfigBuilder withCors(Set<String> allowedOrigins) {
        return withCors(allowedOrigins, DEFAULT_ALLOWED_HEADERS, DEFAULT_ALLOWED_METHODS, DEFAULT_ALLOW_CREDENTIALS);
    }
    
    /**
     * Creates a new builder with CORS enabled with custom configuration.
     * Use this method when you need to customize headers and methods beyond the defaults.
     *
     * @param allowedOrigins Set of allowed origin domains (e.g., "https://example.com")
     * @param allowedHeaders Set of allowed HTTP headers (e.g., "X-Custom-Header")
     * @param allowedMethods Set of allowed HTTP methods (e.g., "GET", "POST", "PUT")
     * @param allowCredentials Whether to allow credentials in CORS requests
     * @return a configured JettyConfigBuilder with CORS enabled
     * @throws IllegalArgumentException if allowedOrigins is null or empty
     * <b>Note:</b> If allowedHeaders or allowedMethods is null, default values will be used
     */
    public static JettyConfigBuilder withCors(
            Set<String> allowedOrigins,
            Set<String> allowedHeaders,
            Set<String> allowedMethods,
            boolean allowCredentials) {
        
        if (allowedOrigins == null || allowedOrigins.isEmpty()) {
            throw new IllegalArgumentException("At least one allowed origin must be specified for CORS configuration");
        }
        if (allowedHeaders == null) {
            allowedHeaders = DEFAULT_ALLOWED_HEADERS;
        }
        if (allowedMethods == null) {
            allowedMethods = DEFAULT_ALLOWED_METHODS;
        }
        
        return builder()
                .corsEnabled(true)
                .allowedOrigins(Set.copyOf(allowedOrigins))
                .allowedHeaders(Set.copyOf(allowedHeaders))
                .allowedMethods(Set.copyOf(allowedMethods))
                .allowCredentials(allowCredentials);
    }
    


    public static class JettyConfigBuilder {
        private String host = DEFAULT_HOST;
        private int port = DEFAULT_PORT;
        private int maxVirtualThreads = DEFAULT_MAX_VIRTUAL_THREADS;
        private int reservedThreads = DEFAULT_RESERVED_THREADS;
        private boolean corsEnabled = DEFAULT_CORS_ENABLED;
        private Set<String> allowedOrigins = DEFAULT_ALLOWED_ORIGINS;
        private Set<String> allowedHeaders = DEFAULT_ALLOWED_HEADERS;
        private Set<String> allowedMethods = DEFAULT_ALLOWED_METHODS;
        private boolean allowCredentials = DEFAULT_ALLOW_CREDENTIALS;

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

        public JettyConfigBuilder corsEnabled(boolean corsEnabled) {
            this.corsEnabled = corsEnabled;
            return this;
        }

        public JettyConfigBuilder allowedOrigins(Set<String> allowedOrigins) {
            this.allowedOrigins = allowedOrigins;
            return this;
        }

        public JettyConfigBuilder allowedHeaders(Set<String> allowedHeaders) {
            this.allowedHeaders = allowedHeaders;
            return this;
        }

        public JettyConfigBuilder allowedMethods(Set<String> allowedMethods) {
            this.allowedMethods = allowedMethods;
            return this;
        }

        public JettyConfigBuilder allowCredentials(boolean allowCredentials) {
            this.allowCredentials = allowCredentials;
            return this;
        }

        /**
         * Builds the JettyConfig instance with validation.
         * 
         * @return a new JettyConfig instance
         * @throws IllegalStateException if CORS is enabled but no allowed origins are configured
         * @throws IllegalStateException if CORS is enabled with allowedCredential but '*' is for allowedOrigins
         */
        public JettyConfig build() {
            // Only validate CORS settings if CORS is enabled
            if (corsEnabled) {
                if (allowedOrigins == null || allowedOrigins.isEmpty()) {
                    throw new IllegalStateException("CORS is enabled but no allowed origins are configured. " +
                            "Use allowedOrigins() to specify at least one origin or disable CORS.");
                }
                
                if (allowedOrigins.contains("*") && allowCredentials) {
                    throw new IllegalStateException(
                        "Cannot use wildcard ('*') origin with allowCredentials=true. " +
                        "Specify exact origins or disable credentials.");
                }
            }
            
            return new JettyConfig(
                    host,
                    port,
                    maxVirtualThreads,
                    reservedThreads,
                    corsEnabled,
                    allowedOrigins,
                    allowedHeaders,
                    allowedMethods,
                    allowCredentials
            );
        }
    }
}
