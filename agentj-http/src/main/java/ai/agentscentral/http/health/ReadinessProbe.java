package ai.agentscentral.http.health;

import ai.agentscentral.http.health.response.ReadinessResponse;

import static jakarta.servlet.http.HttpServletResponse.SC_OK;

/**
 * ReadinessProbe
 *
 * @author Rizwan Idrees
 */
@FunctionalInterface
public interface ReadinessProbe {

    ReadinessResponse probe();

}
