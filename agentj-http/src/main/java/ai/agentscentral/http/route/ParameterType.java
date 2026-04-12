package ai.agentscentral.http.route;

/**
 * Discriminator indicating where a controller method parameter's value is sourced from.
 *
 * @author Rizwan Idrees
 */
enum ParameterType {

    /** Value extracted from the URI path (annotated with {@link ai.agentscentral.http.route.annotations.PathVariable}). */
    PATH,

    /** Value extracted from the query string (annotated with {@link ai.agentscentral.http.route.annotations.RequestParam}). */
    PARAMETER,

    /** Value deserialized from the request body (annotated with {@link ai.agentscentral.http.route.annotations.Body}). */
    BODY,

    /** Value extracted from a request header (annotated with {@link ai.agentscentral.http.route.annotations.Header}). */
    HEADER
}
