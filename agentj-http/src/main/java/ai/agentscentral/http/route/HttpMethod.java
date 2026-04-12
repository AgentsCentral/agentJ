package ai.agentscentral.http.route;

/**
 * Standard HTTP request methods supported by the AgentJ routing layer.
 *
 * @author Rizwan Idrees
 */
public enum HttpMethod {

    /** HTTP GET — retrieves a resource without side effects. */
    GET,

    /** HTTP POST — submits data to create or process a resource. */
    POST,

    /** HTTP PUT — replaces a resource with the supplied representation. */
    PUT,

    /** HTTP DELETE — removes the identified resource. */
    DELETE,

    /** HTTP PATCH — applies partial modifications to a resource. */
    PATCH
}
