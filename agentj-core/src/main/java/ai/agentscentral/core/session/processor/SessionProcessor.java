package ai.agentscentral.core.session.processor;

import ai.agentscentral.core.session.message.Message;
import ai.agentscentral.core.session.message.UserMessage;
import ai.agentscentral.core.session.user.User;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.List;

/**
 * Entry-point interface for processing a user message within a session.
 *
 * <p>A {@code SessionProcessor} is the top-level API boundary between the HTTP layer and
 * the agentic execution framework. It receives a {@link UserMessage}, drives the full
 * turn through the executor chain, and returns only the messages that should be surfaced
 * to the caller (i.e. displayable assistant responses and interrupt prompts). The default
 * implementation is {@link DefaultSessionProcessor}.</p>
 *
 * @author Rizwan Idrees
 */
public interface SessionProcessor {

    /**
     * Processes the given user message within the specified session and returns the
     * displayable messages produced during the turn.
     *
     * @param sessionId the conversation session identifier; used to retrieve and persist
     *                  the context for this conversation
     * @param message   the user message to process
     * @param user      the end-user associated with this request; may be {@code null}
     * @return the subset of new messages that should be surfaced to the caller —
     *         typically assistant text responses and {@link ai.agentscentral.core.session.message.ToolInterruptMessage}s;
     *         internal messages such as tool results and handoff records are excluded
     */
    List<Message> process(@Nonnull String sessionId, @Nonnull UserMessage message, @Nullable User user);
}
