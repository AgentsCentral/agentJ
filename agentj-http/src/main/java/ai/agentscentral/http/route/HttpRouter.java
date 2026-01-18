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

public class HttpRouter implements Router {

    static final Comparator<MatchedRoute> sortByPathLength = comparingInt(o -> o.path().length());
    public static final Response<?> NOT_FOUND = Response.response(404, "application/json")
            .resource(new HttpError("not found")).build();

    private final List<Route> routes;
    private final HttpControllerRouter controllerRouter;

    public HttpRouter(List<Route> routes) {
        this(routes, new DefaultContentConvertor());
    }

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
