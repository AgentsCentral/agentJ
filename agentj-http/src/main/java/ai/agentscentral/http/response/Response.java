package ai.agentscentral.http.response;

import jakarta.annotation.Nonnull;

/**
 * Generic HTTP response envelope carrying an HTTP status code, a content-type, and an
 * optional response body.
 *
 * <p>Instances are created via the {@link #response(Integer, String)} factory method
 * and the fluent {@link ResponseBuilder}:
 * <pre>{@code
 * Response<MessageResponse> r = Response.response(200, "application/json")
 *         .resource(messageResponse)
 *         .build();
 * }</pre>
 *
 * @param <T>         the type of the response body
 * @param status      HTTP status code (e.g. 200, 404, 500)
 * @param contentType MIME type of the response body (e.g. {@code "application/json"})
 * @param resource    the response body; may be {@code null} for status-only responses
 *
 * @author Rizwan Idrees
 */
public record Response<T>(@Nonnull Integer status, @Nonnull String contentType, T resource) {

    /**
     * Creates a new {@link ResponseBuilder} pre-configured with the given status and
     * content-type.
     *
     * @param <T>         the type of the response body
     * @param status      HTTP status code
     * @param contentType MIME type of the response
     * @return a new {@code ResponseBuilder}
     */
    public static <T> ResponseBuilder<T> response(@Nonnull Integer status, @Nonnull String contentType) {
        return new ResponseBuilder<>(status, contentType);
    }

    /**
     * Fluent builder for {@link Response}.
     *
     * @param <T> the type of the response body
     */
    public static class ResponseBuilder<T> {

        private final Integer status;
        private final String contentType;
        private T resource;

        /**
         * Creates a builder with the given status code and content-type.
         *
         * @param status      HTTP status code
         * @param contentType MIME type of the response
         */
        public ResponseBuilder(Integer status, String contentType) {
            this.status = status;
            this.contentType = contentType;
        }

        /**
         * Builds the {@link Response}.
         *
         * @return the assembled response
         */
        public Response<T> build() {
            return new Response<>(status, contentType, resource);
        }

        /**
         * Sets the response body.
         *
         * @param resource the body to include in the response
         * @return this builder
         */
        public ResponseBuilder<T> resource(T resource) {
            this.resource = resource;
            return this;
        }
    }

}
