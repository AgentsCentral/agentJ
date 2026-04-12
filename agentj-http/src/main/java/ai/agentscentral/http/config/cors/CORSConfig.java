package ai.agentscentral.http.config.cors;

import java.util.Set;

/**
 * Immutable configuration record for CORS (Cross-Origin Resource Sharing).
 *
 * @param allowedOrigins   set of origin strings whose cross-origin requests are permitted;
 *                         empty by default — must be explicitly configured for production
 * @param allowedHeaders   set of header names browsers may send in cross-origin requests
 * @param allowedMethods   set of HTTP method strings allowed in cross-origin requests
 * @param allowCredentials {@code true} to send
 *                         {@code Access-Control-Allow-Credentials: true}, permitting
 *                         cookies and HTTP authentication in cross-origin requests
 *
 * @author Mustafa Bhuiyan
 */
public record CORSConfig (Set<String> allowedOrigins,
                          Set<String> allowedHeaders,
                          Set<String> allowedMethods,
                          boolean allowCredentials) {
    /**
     * <p>Default CORS configuration values.</p>
     * <p><b>SECURITY_WARNING:</b> The default CORS configuration is intentionally restrictive.
     *  In production, explicitly configure CORS to allow only trusted origins.
     *  Using "*" as an allowed origin is not recommended for production
     *  as it allows any website to make cross-origin requests to your API.</p>
     */
    public static final boolean DEFAULT_ALLOW_CREDENTIALS = false;
    public static final Set<String> DEFAULT_ALLOWED_ORIGINS = Set.of(); // Empty set by default - must be explicitly configured
    public static final Set<String> DEFAULT_ALLOWED_HEADERS = Set.of("Content-Type", "Authorization");
    public static final Set<String> DEFAULT_ALLOWED_METHODS = Set.of("GET", "HEAD", "OPTIONS");

    /**
     * Returns a {@code CORSConfig} with the default restrictive settings: no allowed
     * origins, {@code Content-Type} and {@code Authorization} headers, {@code GET},
     * {@code HEAD} and {@code OPTIONS} methods, and credentials disabled.
     *
     * @return a restrictive default CORS configuration
     */
    public static CORSConfig defaultCORSConfig() {
        return new CORSConfig(DEFAULT_ALLOWED_ORIGINS, DEFAULT_ALLOWED_HEADERS,
                DEFAULT_ALLOWED_METHODS, DEFAULT_ALLOW_CREDENTIALS);
    }

    /**
     * Returns a new {@link CORSConfigBuilder} pre-populated with the default values.
     *
     * @return a fresh builder
     */
    public static CORSConfigBuilder builder() {
        return new CORSConfigBuilder();
    }

    /**
     * Fluent builder for {@link CORSConfig}.
     *
     * <p>Initialised with the default restrictive values; override only the settings
     * that differ from the defaults.</p>
     */
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

        /**
         * Sets the allowed origins. Only requests whose {@code Origin} header matches
         * an entry in this set will receive CORS response headers.
         *
         * @param allowedOrigins the set of permitted origin strings
         * @return this builder
         */
        public CORSConfigBuilder allowedOrigins(Set<String> allowedOrigins) {
            this.allowedOrigins = allowedOrigins;
            return this;
        }

        /**
         * Sets the headers that browsers are permitted to send in cross-origin requests.
         *
         * @param allowedHeaders the set of permitted header names
         * @return this builder
         */
        public CORSConfigBuilder allowedHeaders(Set<String> allowedHeaders) {
            this.allowedHeaders = allowedHeaders;
            return this;
        }

        /**
         * Sets the HTTP methods permitted in cross-origin requests.
         *
         * @param allowedMethods the set of allowed HTTP method strings (e.g. {@code "POST"})
         * @return this builder
         */
        public CORSConfigBuilder allowedMethods(Set<String> allowedMethods) {
            this.allowedMethods = allowedMethods;
            return this;
        }

        /**
         * Controls whether the {@code Access-Control-Allow-Credentials: true} header is
         * sent, permitting cross-origin requests that include cookies or HTTP
         * authentication.
         *
         * @param allowCredentials {@code true} to allow credentials; {@code false}
         *                         (default) to disallow
         * @return this builder
         */
        public CORSConfigBuilder allowCredentials(boolean allowCredentials) {
            this.allowCredentials = allowCredentials;
            return this;
        }

        /**
         * Builds the {@link CORSConfig}.
         *
         * @return the assembled CORS configuration
         */
        public CORSConfig build() {
            return new CORSConfig(allowedOrigins, allowedHeaders, allowedMethods, allowCredentials);
        }
    }
}
