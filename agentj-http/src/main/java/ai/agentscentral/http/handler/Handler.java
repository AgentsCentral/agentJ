package ai.agentscentral.http.handler;

/**
 * Root marker interface for all request handlers in the AgentJ HTTP framework.
 *
 * <p>Subtypes specialise this interface for specific handler shapes:
 * <ul>
 *   <li>{@link HttpHandler} — a functional handler that maps a
 *       {@link ai.agentscentral.http.request.Request} to a
 *       {@link ai.agentscentral.http.response.Response}</li>
 * </ul>
 *
 * @author Rizwan Idrees
 */
public interface Handler {
}
