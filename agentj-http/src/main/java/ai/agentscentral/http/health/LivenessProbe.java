package ai.agentscentral.http.health;

import ai.agentscentral.http.health.response.LivenessResponse;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;

/**
 * LivenessProbe
 *
 * @author Rizwan Idrees
 */
@FunctionalInterface
public interface LivenessProbe {

    LivenessResponse probe();

}
