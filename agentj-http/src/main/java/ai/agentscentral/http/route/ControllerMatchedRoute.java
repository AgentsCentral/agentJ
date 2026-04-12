package ai.agentscentral.http.route;

/**
 * {@link MatchedRoute} produced by a successful {@link ControllerRoute} match.
 *
 * <p>Carries the matched path, the controller instance, and the
 * {@link ControllerMappedMatchedRoute} that identifies the specific method to invoke.
 * {@link HttpRouter} forwards this to {@link HttpControllerRouter} for reflective
 * dispatch.</p>
 *
 * @param path               the matched route path
 * @param controller         the controller instance whose method should be invoked
 * @param mappedMatchedRoute details of the specific mapped method and its parameters
 *
 * @author Rizwan Idrees
 */
public record ControllerMatchedRoute(String path, Object controller, ControllerMappedMatchedRoute mappedMatchedRoute) implements MatchedRoute {
}
