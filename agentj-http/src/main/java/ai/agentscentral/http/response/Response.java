package ai.agentscentral.http.response;

public class Response<T> {

    private final int status;
    private final String contentType;
    private final T resource;

    public Response(int status, String contentType, T resource) {
        this.status = status;
        this.contentType = contentType;
        this.resource = resource;
    }

    public int getStatus() {
        return status;
    }

    public String getContentType() {
        return contentType;
    }

    public T getT() {
        return resource;
    }

    public static class ResponseBuilder<T> {

        private int status;
        private String contentType;
        private T resource;

        public Response<T> build() {
            return new Response<>(status, contentType, resource);
        }

        public ResponseBuilder<T> status(int status) {
            this.status = status;
            return this;
        }

        public ResponseBuilder<T> contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public ResponseBuilder<T> resource(T resource) {
            this.resource = resource;
            return this;
        }
    }

}
