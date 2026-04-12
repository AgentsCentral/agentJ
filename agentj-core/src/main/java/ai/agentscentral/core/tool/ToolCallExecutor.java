package ai.agentscentral.core.tool;

import ai.agentscentral.core.session.message.InterruptParameterValue;
import ai.agentscentral.core.session.user.User;

import java.util.List;
import java.util.Optional;

/**
 * Executes a tool call and returns the result or a structured error.
 *
 * <p>The default implementation is {@link DefaultToolCallExecutor}, which invokes the
 * tool method via reflection, resolving both LLM-supplied {@link ToolParameter} values
 * from the instruction arguments and user-supplied
 * {@link ai.agentscentral.core.session.message.InterruptParameterValue}s from interrupt
 * responses.</p>
 *
 * @param <T> the message type produced by the result/error convertors (typically
 *            {@link ai.agentscentral.core.session.message.ToolMessage})
 *
 * @author Rizwan Idrees
 */
public interface ToolCallExecutor<T> {

    /**
     * Executes a named pre-interrupt call function for the given user.
     *
     * <p>Pre-calls are looked up by name in the
     * {@link ai.agentscentral.core.agentic.executor.register.InterruptPreCallRegistrar}
     * and their results are attached to the interrupt prompt as contextual data.</p>
     *
     * @param <R>  the expected return type of the pre-call function
     * @param name the registered name of the {@link PreInterruptCall} to invoke
     * @param user the current end-user, passed to the pre-call function
     * @return an {@code Optional} containing the pre-call result, or empty if no function
     *         is registered under {@code name}
     */
    <R> Optional<R> executePreCall(String name, User user);

    /**
     * Invokes the tool method described by {@code toolCall} with arguments from
     * {@code instruction} and any user-supplied {@code interruptParameters}.
     *
     * @param instruction        the tool call instruction containing the call id and
     *                           resolved argument map from the provider
     * @param toolCall           the descriptor holding the reflected method, tool bag
     *                           instance, and parameter metadata
     * @param interruptParameters user-supplied values for
     *                           {@link ai.agentscentral.core.annotation.InterruptParam}-annotated
     *                           parameters; empty when there are no interrupt parameters
     * @return a {@link ResultOrError} holding either a {@link ToolCallResult} on success
     *         or a {@link ToolCallExecutionError} on failure
     */
    ResultOrError<ToolCallResult, ToolCallExecutionError, T> execute(ToolCallInstruction instruction,
                                                                     ToolCall toolCall,
                                                                     List<InterruptParameterValue> interruptParameters);

}
