package ai.agentscentral.http.route;

import ai.agentscentral.http.request.Request;
import ai.agentscentral.http.response.HttpError;
import ai.agentscentral.http.response.Response;
import ai.agentscentral.http.route.convertors.ContentConvertor;
import ai.agentscentral.http.route.convertors.DefaultContentConvertor;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparingInt;

/**
 * {@link Router} implementation that evaluates all registered {@link Route}s against
 * the inbound request and dispatches to the longest-matching route.
 *
 * <p>When multiple routes match, the one with the longest {@link MatchedRoute#path()} is
 * preferred (most-specific wins).  If no route matches, the pre-built
 * {@link #NOT_FOUND} response is returned.</p>
 *
 * <p>Handler-based routes ({@link HandlerMatchedRoute}) are dispatched directly to the
 * associated {@link ai.agentscentral.http.handler.HttpHandler}; controller-based routes
 * ({@link ControllerMatchedRoute}) are forwarded to the internal
 * {@link HttpControllerRouter}.</p>
 *
 * @author Rizwan Idrees
 */
public class HttpRouter implements Router {

    static final Comparator<MatchedRoute> sortByPathLength = comparingInt(o -> o.path().length());
    public static final Response<?> NOT_FOUND = Response.response(404, "application/json")
            .resource(new HttpError("not found")).build();

    private final List<Route> routes;
    private final HttpControllerRouter controllerRouter;

    /**
     * Creates an {@code HttpRouter} with the given routes and a default
     * {@link DefaultContentConvertor} for controller-based routes.
     *
     * @param routes the list of registered routes
     */
    public HttpRouter(List<Route> routes) {
        this(routes, new DefaultContentConvertor());
    }

    /**
     * Creates an {@code HttpRouter} with the given routes and a custom
     * {@link ContentConvertor} for controller-based routes.
     *
     * @param routes           the list of registered routes
     * @param contentConvertor convertor used to serialise/deserialise controller bodies
     */
    public HttpRouter(List<Route> routes, ContentConvertor contentConvertor) {
        this.routes = routes;
        this.controllerRouter = new HttpControllerRouter(contentConvertor);
    }

    @Override
    public Response<?> route(Request request) {
        final Optional<MatchedRoute> matchedRoute = routes.stream()
                .flatMap(r -> r.match(request).stream())
                .max(sortByPathLength);

        return matchedRoute.<Response<?>>map(mr -> routeToMatched(mr, request))
                .orElse(NOT_FOUND);
    }

    private Response<?> routeToMatched(MatchedRoute matchedRoute, Request request) {
        return switch (matchedRoute) {
            case HandlerMatchedRoute hmr -> hmr.handler().handle(request);
            case ControllerMatchedRoute cmr -> controllerRouter.route(cmr, request);
            default -> NOT_FOUND;
        };
    }

}
