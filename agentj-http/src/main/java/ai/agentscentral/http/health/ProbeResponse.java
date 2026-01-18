package ai.agentscentral.http.health;

import java.util.Map;

/**
 * ProbeResponse
 *
 * @param status
 * @param data
 * @author Rizwan Idrees
 */
public record ProbeResponse(Integer status, Map<String, Object> data) {

    public ProbeResponse(Integer status) {
        this(status, Map.of());
    }
}
