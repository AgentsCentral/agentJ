package ai.agentscentral.core.session.id;

/**
 * Generates unique identifiers for conversation sessions.
 *
 * <p>Session ids are used as the primary key under which all messages belonging to a
 * conversation are stored in the {@link ai.agentscentral.core.context.ContextManager}.
 * The default implementation is {@link DefaultSessionIdGenerator}.</p>
 *
 * @author Rizwan Idrees
 */
public interface SessionIdGenerator {

    /**
     * Generates and returns a new unique session identifier.
     *
     * @return a non-null, unique session id
     */
    String generate();

}
