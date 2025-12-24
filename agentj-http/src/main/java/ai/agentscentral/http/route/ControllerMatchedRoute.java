package ai.agentscentral.http.route;

import java.lang.reflect.Method;

/**
 * ControllerMatchedRoute
 *
 * @param path
 * @param controller
 * @param method
 * @author Rizwan Idrees
 */

public record ControllerMatchedRoute(String path, Object controller, Method method) implements MatchedRoute {
}
