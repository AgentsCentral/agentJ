package ai.agentscentral.core.tool;

import ai.agentscentral.core.session.message.InterruptParameterValue;
import ai.agentscentral.core.session.user.User;

import java.util.List;
import java.util.Optional;

/**
 * ToolCallExecutor
 *
 * @param <T>
 * @author Rizwan Idrees
 */
public interface ToolCallExecutor<T> {


    <R> Optional<R> executePreCall(String name, User user);

    ResultOrError<ToolCallResult, ToolCallExecutionError, T> execute(ToolCallInstruction instruction,
                                                                     List<InterruptParameterValue> interruptParameters);

}
