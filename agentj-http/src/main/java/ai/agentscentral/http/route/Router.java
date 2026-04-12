package ai.agentscentral.http.route;

import ai.agentscentral.http.request.Request;
import ai.agentscentral.http.response.Response;

/**
 * Top-level routing interface that dispatches an inbound {@link Request} and returns
 * a {@link Response}.
 *
 * <p>The primary implementation is {@link HttpRouter}, which iterates over a list of
 * registered {@link Route}s and delegates to the best match.</p>
 *
 * @author Rizwan Idrees
 */
public interface Router {

    /**
     * Routes the given request and returns the appropriate response.
     *
     * @param request the inbound HTTP request
     * @return the response produced by the matched route; never {@code null}
     */
    Response route(Request request);

}
