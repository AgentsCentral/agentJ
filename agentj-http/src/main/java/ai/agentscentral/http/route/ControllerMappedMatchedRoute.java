package ai.agentscentral.http.route;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Internal {@link MatchedRoute} carrying the fully resolved dispatch context for a
 * controller method that matched the incoming request.
 *
 * <p>Used by {@link HttpControllerRouter} to reflectively invoke the method and bind its
 * parameters.</p>
 *
 * @param path             the matched route path (may contain {@code {variable}} segments)
 * @param method           the reflected {@link java.lang.reflect.Method} to invoke
 * @param pathPattern      compiled path pattern used to extract path-variable values
 * @param methodParameters ordered list of parameter descriptors for argument binding
 * @param route            the originating {@link ControllerMappedRoute}
 *
 * @author Rizwan Idrees
 */
public record ControllerMappedMatchedRoute(String path,
                                           Method method,
                                           PathPattern pathPattern,
                                           List<MethodParameter> methodParameters,
                                           ControllerMappedRoute route) implements MatchedRoute {
}
