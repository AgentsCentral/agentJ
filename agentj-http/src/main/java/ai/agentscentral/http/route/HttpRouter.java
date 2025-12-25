package ai.agentscentral.http.route;

import ai.agentscentral.http.request.Request;
import ai.agentscentral.http.response.Response;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

import static java.util.Comparator.comparingInt;

public class HttpRouter implements Router {

    static final Comparator<MatchedRoute> sortByPathLength = comparingInt(o -> o.path().length());

    private final Set<Route> routes;

    public HttpRouter(Set<Route> routes) {
        this.routes = routes;
    }

    @Override
    public Response route(Request request) {
        final Optional<MatchedRoute> matchedRoute = routes.stream()
                .flatMap(r -> r.match(request).stream())
                .max(sortByPathLength);
        return null;
    }
}
