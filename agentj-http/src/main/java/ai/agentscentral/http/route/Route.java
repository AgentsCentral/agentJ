package ai.agentscentral.http.route;

import ai.agentscentral.http.request.Request;

import java.util.Optional;

public interface Route {

    Optional<? extends MatchedRoute> match(Request request);
}
