package ai.agentscentral.http.route;

/**
 * ControllerMatchedRoute
 *
 * @param path
 * @param controller
 * @param mappedMatchedRoute
 * @author Rizwan Idrees
 */

public record ControllerMatchedRoute(String path, Object controller, ControllerMappedMatchedRoute mappedMatchedRoute) implements MatchedRoute {
}
