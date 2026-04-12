package ai.agentscentral.http.route;

import ai.agentscentral.http.request.Request;

import java.util.Optional;

/**
 * Strategy interface for matching an inbound {@link Request} against a registered route.
 *
 * <p>Concrete implementations include {@link HttpHandlerRoute} (handler-based routing)
 * and {@link ControllerRoute} (annotation-driven controller routing).  The
 * {@link HttpRouter} iterates over all registered {@code Route}s and selects the
 * best match.</p>
 *
 * @author Rizwan Idrees
 */
public interface Route {

    /**
     * Attempts to match the given request against this route.
     *
     * @param request the inbound HTTP request
     * @return an {@link Optional} containing the {@link MatchedRoute} if this route
     *         matches, or {@link Optional#empty()} otherwise
     */
    Optional<MatchedRoute> match(Request request);
}
