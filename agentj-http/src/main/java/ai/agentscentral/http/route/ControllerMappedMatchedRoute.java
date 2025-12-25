package ai.agentscentral.http.route;

import java.lang.reflect.Method;

/**
 * ControllerMappedMatchedRoute
 *
 * @param path
 * @param method
 * @param route
 * @author Rizwan Idrees
 */
public record ControllerMappedMatchedRoute(String path, Method method,
                                           ControllerMappedRoute route) implements MatchedRoute {
}
