package ai.agentscentral.core.context;

import ai.agentscentral.core.session.message.Message;

import java.util.List;

/**
 * Manages the per-context message history for an agent or team session.
 *
 * <p>A <em>context</em> is identified by a {@code contextId} string and holds the ordered
 * list of {@link Message}s exchanged so far — user messages, assistant responses, tool
 * results, handoff records, and interrupt messages.  The context is the primary input
 * supplied to {@link ai.agentscentral.core.provider.ProviderAgentExecutor#execute} on each
 * conversational turn.</p>
 *
 * <p>Implementations must be thread-safe, as multiple agents running concurrently may
 * read and append to different contexts simultaneously.</p>
 *
 * @author Rizwan Idrees
 */
public interface ContextManager {

    /**
     * Returns the current message history for the given context, sorted by timestamp.
     *
     * <p>If no history exists for {@code contextId} yet, an empty, mutable list is created
     * and stored so that subsequent {@link #addContext} calls can populate it.</p>
     *
     * @param contextId the unique identifier for the conversation context
     * @return a mutable, timestamp-ordered list of all messages in the context;
     *         never {@code null}, may be empty
     */
    List<Message> getContext(String contextId);

    /**
     * Appends one or more messages to the history for the given context.
     *
     * <p>If the context does not yet exist it is created implicitly.  The messages are
     * appended in the order they appear in {@code newMessages}.</p>
     *
     * @param contextId   the unique identifier for the conversation context
     * @param newMessages the messages to append; must not be {@code null}
     */
    void addContext(String contextId, List<? extends Message> newMessages);

}
