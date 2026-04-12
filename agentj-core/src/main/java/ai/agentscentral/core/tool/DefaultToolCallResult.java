package ai.agentscentral.core.tool;

/**
 * Default immutable implementation of {@link ToolCallResult}.
 *
 * <p>Created by {@link DefaultToolCallExecutor} after a successful reflective method
 * invocation and passed to {@link ResultOrError#ofResult}.</p>
 *
 * @param instruction the original {@link ToolCallInstruction} that triggered the call
 * @param result      the value returned by the tool method; may be {@code null}
 * @param returnType  the declared return type of the tool method
 *
 * @author Rizwan Idrees
 */
public record DefaultToolCallResult(ToolCallInstruction instruction, Object result, Class<?> returnType) implements ToolCallResult {
}
