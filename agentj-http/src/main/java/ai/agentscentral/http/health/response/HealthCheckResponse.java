package ai.agentscentral.http.health.response;

import java.util.Map;

/**
 * HealthCheckResponse
 *
 * @param liveness
 * @param readiness
 * @author Rizwan Idrees
 */
public record HealthCheckResponse(Map<String, Object> liveness, Map<String, Object> readiness) {
}
