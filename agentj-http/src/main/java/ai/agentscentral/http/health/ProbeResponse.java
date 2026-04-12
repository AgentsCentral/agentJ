package ai.agentscentral.http.health;

import java.util.Map;

/**
 * The result of a {@link Probe} invocation, carrying an HTTP status code and optional
 * diagnostic data.
 *
 * @param status the HTTP status code to return (e.g. {@code 200} for healthy,
 *               {@code 503} for unhealthy)
 * @param data   an optional map of key/value pairs included in the response body for
 *               diagnostic purposes; never {@code null}
 *
 * @author Rizwan Idrees
 */
public record ProbeResponse(Integer status, Map<String, Object> data) {

    /**
     * Creates a {@code ProbeResponse} with the given status and an empty data map.
     *
     * @param status the HTTP status code
     */
    public ProbeResponse(Integer status) {
        this(status, Map.of());
    }
}
