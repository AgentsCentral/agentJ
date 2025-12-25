package ai.agentscentral.http.route;

import java.lang.reflect.Method;

/**
 * ControllerMappedMatchedRoute
 *
 * @param path
 * @param method
 * @param pathPattern
 * @param route
 * @author Rizwan Idrees
 */
public record ControllerMappedMatchedRoute(String path,
                                           Method method,
                                           PathPattern pathPattern,
                                           ControllerMappedRoute route) implements MatchedRoute {
}
