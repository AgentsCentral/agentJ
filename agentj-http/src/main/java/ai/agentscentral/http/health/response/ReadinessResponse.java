package ai.agentscentral.http.health.response;

import java.util.Map;

/**
 * ReadinessResponse
 *
 * @author Rizwan Idrees
 */
public class ReadinessResponse {

    private final int httpStatus;
    private final Map<String, Object> body;

    public ReadinessResponse(int httpStatus) {
        this(httpStatus, Map.of("status", "OK"));
    }

    public ReadinessResponse(int httpStatus, Map<String, Object> body) {
        this.httpStatus = httpStatus;
        this.body = body;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public Map<String, Object> getBody() {
        return body;
    }
}
