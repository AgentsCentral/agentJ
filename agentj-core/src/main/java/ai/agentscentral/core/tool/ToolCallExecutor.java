package ai.agentscentral.core.tool;

import ai.agentscentral.core.session.message.InterruptParameterValue;

import java.util.List;

/**
 * ToolCallExecutor
 * @param <T>
 *
 * @author Rizwan Idrees
 */
public interface ToolCallExecutor<T> {

    ResultOrError<ToolCallResult, ToolCallExecutionError, T> execute(ToolCallInstruction instruction, List<InterruptParameterValue> interruptParameters);

}
