package ai.agentscentral.http.health;

import ai.agentscentral.http.health.response.LivenessResponse;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;

/**
 * LivenessProbe
 *
 * @author Rizwan Idrees
 */
public interface LivenessProbe {

    default LivenessResponse probe() {
        return new LivenessResponse(SC_OK);
    }

}
