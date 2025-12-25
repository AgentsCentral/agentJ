package ai.agentscentral.http.route;

/**
 * ControllerMatchedRoute
 *
 * @param path
 * @param controller
 * @param route
 * @author Rizwan Idrees
 */

public record ControllerMatchedRoute(String path, Object controller, ControllerMappedMatchedRoute route) implements MatchedRoute {
}
