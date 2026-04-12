package ai.agentscentral.core.provider;

import ai.agentscentral.core.session.message.AssistantMessage;
import ai.agentscentral.core.session.message.Message;
import ai.agentscentral.core.session.user.User;

import java.util.List;

/**
 * Handles the low-level communication with an LLM provider for a single agent.
 *
 * <p>Implementations are created by {@link ai.agentscentral.core.model.ModelConfig#createAgentExecutor}
 * and are specific to a provider (e.g. Anthropic, OpenAI). Each implementation is
 * responsible for converting the framework's {@link Message} list into the provider's
 * request format, calling the provider's API via the corresponding
 * {@link ProviderClient}, and parsing the response back into one or more
 * {@link AssistantMessage}s that the framework can act on (tool calls, handoffs, or
 * plain text).</p>
 *
 * <p>This interface is called by
 * {@link ai.agentscentral.core.agentic.executor.DefaultAgentExecutor} on every pass of
 * the tool-call loop — once for the initial user message and again after each round of
 * tool results until the model returns a terminal response.</p>
 *
 * @author Rizwan Idrees
 */
public interface ProviderAgentExecutor {

    /**
     * Sends the current conversation context to the LLM provider and returns the
     * assistant response(s).
     *
     * @param contextId the conversation context identifier, passed through for
     *                  correlation and logging purposes
     * @param user      the end-user associated with this request; may be {@code null}
     * @param messages  the full conversation history for this turn, including the
     *                  original user message, any prior assistant messages, and tool
     *                  results accumulated so far
     * @return one or more {@link AssistantMessage}s produced by the provider; each may
     *         contain text content, tool call instructions, or handoff instructions
     */
    List<AssistantMessage> execute(String contextId, User user, List<Message> messages);
}
