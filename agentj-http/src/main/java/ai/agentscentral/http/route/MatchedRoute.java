package ai.agentscentral.http.route;

/**
 * Represents the outcome of a successful route match.
 *
 * <p>Subtypes carry the additional context needed to dispatch the request:
 * <ul>
 *   <li>{@link HandlerMatchedRoute} — for {@link HttpHandlerRoute}-based matches</li>
 *   <li>{@link ControllerMatchedRoute} — for {@link ControllerRoute}-based matches</li>
 *   <li>{@link ControllerMappedMatchedRoute} — internal; carries the reflected
 *       method details for a specific controller endpoint</li>
 * </ul>
 *
 * @author Rizwan Idrees
 */
public interface MatchedRoute {

    /**
     * Returns the matched route path (may contain path-variable placeholders such as
     * {@code {id}}).
     *
     * @return the route path string
     */
    String path();
}
