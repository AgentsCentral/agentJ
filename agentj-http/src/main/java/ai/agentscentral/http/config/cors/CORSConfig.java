package ai.agentscentral.http.config.cors;

import java.util.Set;

/**
 * Configuration Class for CORS (Cross-Origin Resource Sharing)
 *
 * @param allowedOrigins
 * @param allowedHeaders
 * @param allowedMethods
 * @param allowCredentials
 *
 * @author Mustafa Bhuiyan
 */
public record CORSConfig (Set<String> allowedOrigins,
                          Set<String> allowedHeaders,
                          Set<String> allowedMethods,
                          boolean allowCredentials) {
    /**
     * <p>Default CORS configuration values.<p/>
     * <p><b>SECURITY_WARNING:</b> The default CORS configuration is intentionally restrictive.
     *  In production, explicitly configure CORS to allow only trusted origins.
     *  Using "*" as an allowed origin is not recommended for production
     *  as it allows any website to make cross-origin requests to your API.</p>
     */
    public static final boolean DEFAULT_ALLOW_CREDENTIALS = false;
    public static final Set<String> DEFAULT_ALLOWED_ORIGINS = Set.of(); // Empty set by default - must be explicitly configured
    public static final Set<String> DEFAULT_ALLOWED_HEADERS = Set.of("Content-Type", "Authorization");
    public static final Set<String> DEFAULT_ALLOWED_METHODS = Set.of("GET", "HEAD", "OPTIONS");

    public static CORSConfig defaultCORSConfig() {
        return new CORSConfig(DEFAULT_ALLOWED_ORIGINS, DEFAULT_ALLOWED_HEADERS,
                DEFAULT_ALLOWED_METHODS, DEFAULT_ALLOW_CREDENTIALS);
    }

    public static CORSConfigBuilder builder() {
        return new CORSConfigBuilder();
    }

    public static class CORSConfigBuilder {
        private Set<String> allowedOrigins;
        private Set<String> allowedHeaders;
        private Set<String> allowedMethods;
        private boolean allowCredentials;

        public CORSConfigBuilder() {
            this.allowedOrigins = DEFAULT_ALLOWED_ORIGINS;
            this.allowedHeaders = DEFAULT_ALLOWED_HEADERS;
            this.allowedMethods = DEFAULT_ALLOWED_METHODS;
            this.allowCredentials = DEFAULT_ALLOW_CREDENTIALS;
        }

        public CORSConfigBuilder allowedOrigins(Set<String> allowedOrigins) {
            this.allowedOrigins = allowedOrigins;
            return this;
        }

        public CORSConfigBuilder allowedHeaders(Set<String> allowedHeaders) {
            this.allowedHeaders = allowedHeaders;
            return this;
        }

        public CORSConfigBuilder allowedMethods(Set<String> allowedMethods) {
            this.allowedMethods = allowedMethods;
            return this;
        }

        public CORSConfigBuilder allowCredentials(boolean allowCredentials) {
            this.allowCredentials = allowCredentials;
            return this;
        }

        public CORSConfig build() {
            return new CORSConfig(allowedOrigins, allowedHeaders, allowedMethods, allowCredentials);
        }
    }
}
