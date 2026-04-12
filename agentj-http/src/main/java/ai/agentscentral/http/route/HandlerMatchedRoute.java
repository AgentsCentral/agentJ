package ai.agentscentral.http.route;

import ai.agentscentral.http.handler.HttpHandler;

/**
 * {@link MatchedRoute} produced by a successful {@link HttpHandlerRoute} match.
 *
 * <p>Carries the matched path, the originating route, and the {@link HttpHandler} that
 * should handle the request.  {@link HttpRouter} dispatches directly to
 * {@link #handler()} when it encounters this type.</p>
 *
 * @param path    the matched route path
 * @param route   the {@link HttpHandlerRoute} that produced this match
 * @param handler the {@link HttpHandler} to invoke for the matched request
 *
 * @author Rizwan Idrees
 */
public record HandlerMatchedRoute(String path,
                                  HttpHandlerRoute route,
                                  HttpHandler handler) implements MatchedRoute {
}
