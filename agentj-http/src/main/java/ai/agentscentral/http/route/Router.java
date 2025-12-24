package ai.agentscentral.http.route;

import java.util.Set;

public class Router {

    private final Set<Route> routes;

    public Router(Set<Route> routes) {
        this.routes = routes;
    }
}
