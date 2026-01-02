package ai.agentscentral.http.response;

public record Response<T>(int status, String contentType, T resource) {

    public static <T> ResponseBuilder<T> builder(){
        return new ResponseBuilder<>();
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
