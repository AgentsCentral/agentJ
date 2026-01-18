package ai.agentscentral.http.response;

import jakarta.annotation.Nonnull;

public record Response<T>(@Nonnull Integer status, @Nonnull String contentType, T resource) {

    public static <T> ResponseBuilder<T> response(@Nonnull Integer status, @Nonnull String contentType) {
        return new ResponseBuilder<>(status, contentType);
    }


    public static class ResponseBuilder<T> {

        private final Integer status;
        private final String contentType;
        private T resource;

        public ResponseBuilder(Integer status, String contentType) {
            this.status = status;
            this.contentType = contentType;
        }

        public Response<T> build() {
            return new Response<>(status, contentType, resource);
        }


        public ResponseBuilder<T> resource(T resource) {
            this.resource = resource;
            return this;
        }
    }

}
