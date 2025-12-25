package ai.agentscentral.http.route;

import java.lang.reflect.Method;

public record ControllerMappedMatchedRoute(String path, Method method, ControllerMappedRoute route) implements MatchedRoute {
}
