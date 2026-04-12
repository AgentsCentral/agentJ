package ai.agentscentral.http.response;

/**
 * JSON error payload returned by the HTTP routing layer when a request cannot be handled.
 *
 * <p>Used as the response body for 404 (not found) and 500 (internal server error)
 * responses produced by {@link ai.agentscentral.http.route.HttpRouter} and
 * {@link ai.agentscentral.http.route.HttpControllerRouter}.</p>
 *
 * @param message human-readable description of the error
 *
 * @author Rizwan Idrees
 */
public record HttpError(String message) {
}
