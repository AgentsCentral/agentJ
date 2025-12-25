package ai.agentscentral.http.route;

import ai.agentscentral.http.handler.HttpHandler;

/**
 * HandlerMatchedRoute
 *
 * @author Rizwan Idrees
 */
public record HandlerMatchedRoute(String path,
                                  HttpHandlerRoute route,
                                  HttpHandler handler) implements MatchedRoute {
}
