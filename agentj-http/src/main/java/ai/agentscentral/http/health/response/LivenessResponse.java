package ai.agentscentral.http.health.response;

import java.util.Map;

/**
 * LivenessResponse
 *
 * @author Rizwan Idrees
 */
public class LivenessResponse {

    private final int httpStatus;
    private final Map<String, Object> body;

    public LivenessResponse(int httpStatus) {
        this(httpStatus, Map.of("status", "OK"));
    }

    public LivenessResponse(int httpStatus, Map<String, Object> body) {
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
