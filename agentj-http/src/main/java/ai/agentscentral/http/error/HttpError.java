package ai.agentscentral.http.error;

/**
 * Wire-format error record returned in the response body when the server encounters an
 * unhandled error (e.g. a route not found or an unexpected exception).
 *
 * <p>Serialised to JSON as {@code {"message":"..."}}.  See also
 * {@link ai.agentscentral.http.response.HttpError} which serves the same purpose within
 * the response layer.</p>
 *
 * @param message a human-readable description of the error
 *
 * @author Rizwan Idrees
 */
public record HttpError(String message) {
}
