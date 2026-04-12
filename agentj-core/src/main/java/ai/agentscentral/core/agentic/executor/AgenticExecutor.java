package ai.agentscentral.core.agentic.executor;

import ai.agentscentral.core.agentic.Agentic;
import ai.agentscentral.core.session.message.Message;
import ai.agentscentral.core.session.message.UserMessage;
import ai.agentscentral.core.session.user.User;

import java.util.List;

/**
 * Core execution contract for any {@link Agentic} entity (agent or team).
 *
 * <p>An {@code AgenticExecutor} drives a single turn of an agentic conversation: it receives
 * the full context, coordinates tool calls, handoffs, and interrupts, and returns the
 * accumulated list of new messages produced during that turn.</p>
 *
 * <p>Specialisations include {@link AgentExecutor} for individual agents and
 * {@link TeamExecutor} for multi-agent teams.</p>
 *
 * @param <T> the concrete {@link Agentic} type managed by this executor
 *
 * @author Rizwan Idrees
 */
public interface AgenticExecutor<T extends Agentic> {

    /**
     * Returns the {@link Agentic} entity (agent or team) managed by this executor.
     *
     * @return the agentic entity; never {@code null}
     */
    T getAgentic();

    /**
     * Executes one conversational turn for the managed agentic entity.
     *
     * <p>Implementations are expected to call the underlying model provider, handle any
     * tool calls or handoffs present in the response, persist new messages via the context
     * manager, and recurse as necessary until a terminal state is reached or an execution
     * limit is exceeded.</p>
     *
     * @param contextId          identifier of the conversation context (session)
     * @param user               the end-user associated with this request; may be {@code null}
     * @param userMessage        the current user message that triggered this turn
     * @param previousContext    messages already present in the context before this turn
     * @param newMessages        messages accumulated so far within this turn; mutated in place
     *                           and returned as the result
     * @param currentAgenticName name of the agentic entity that should handle the request;
     *                           used for routing within teams
     * @param executionContext   per-message execution state tracking tool-call and handoff
     *                           counts and interrupt status
     * @return the updated {@code newMessages} list containing all messages produced in this turn
     */
    List<Message> execute(String contextId,
                          User user,
                          UserMessage userMessage,
                          List<Message> previousContext,
                          List<Message> newMessages,
                          String currentAgenticName,
                          MessageExecutionContext executionContext);
}
