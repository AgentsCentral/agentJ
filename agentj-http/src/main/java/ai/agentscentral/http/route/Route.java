package ai.agentscentral.http.route;

import ai.agentscentral.http.request.Request;

import java.util.Optional;

/**
 * Route
 *
 * @author Rizwan Idrees
 */
public interface Route {

    Optional<? extends MatchedRoute> match(Request request);
}
