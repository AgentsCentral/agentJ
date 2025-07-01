package ai.agentscentral.core.tool;

/**
 * ToolCallExecutor
 * @param <T>
 *
 * @author Rizwan Idrees
 */
public interface ToolCallExecutor<T> {

    ResultOrError<ToolCallResult, ToolCallExecutionError, T> execute(ToolCallInstruction instruction);

}
