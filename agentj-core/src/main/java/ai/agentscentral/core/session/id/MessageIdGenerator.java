package ai.agentscentral.core.session.id;

/**
 * Generates unique identifiers for individual messages within a conversation context.
 *
 * <p>Message ids are assigned to every {@link ai.agentscentral.core.session.message.Message}
 * created during a turn, allowing messages to be correlated across the context history
 * (e.g. matching a {@link ai.agentscentral.core.session.message.ToolMessage} back to its
 * originating {@link ai.agentscentral.core.tool.ToolCallInstruction}). The default
 * implementation is {@link DefaultMessageIdGenerator}.</p>
 *
 * @author Rizwan Idrees
 */
public interface MessageIdGenerator {

    /**
     * Generates and returns a new unique message identifier.
     *
     * @return a non-null, unique message id
     */
    String generate();

}
