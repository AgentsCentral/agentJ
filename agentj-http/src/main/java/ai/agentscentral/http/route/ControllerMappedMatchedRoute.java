package ai.agentscentral.http.route;

import java.lang.reflect.Method;
import java.util.List;

/**
 * ControllerMappedMatchedRoute
 *
 * @param path
 * @param method
 * @param pathPattern
 * @param methodParameters
 * @param route
 * @author Rizwan Idrees
 */
public record ControllerMappedMatchedRoute(String path,
                                           Method method,
                                           PathPattern pathPattern,
                                           List<MethodParameter> methodParameters,
                                           ControllerMappedRoute route) implements MatchedRoute {
}
