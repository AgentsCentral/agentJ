package ai.agentscentral.core.tool;

import ai.agentscentral.core.error.Error;

/**
 * Represents a failure that occurred while executing a tool call.
 *
 * <p>Created by {@link DefaultToolCallExecutor} when a reflective method invocation
 * throws an exception and wrapped in a {@link ResultOrError#ofError}. The error is then
 * converted into a {@link ai.agentscentral.core.session.message.ToolMessage} by
 * {@link ai.agentscentral.core.tool.convertors.ToolConvertors#convertToolCallExecutionErrorToToolMessage}
 * so the provider receives an error message in place of a result, allowing the model to
 * handle the failure gracefully.</p>
 *
 * @param toolCallId the id of the {@link ToolCallInstruction} that failed; used to
 *                   correlate the error message with the original call
 * @param toolName   the name of the tool that failed
 * @param error      a human-readable description of the failure
 *
 * @author Rizwan Idrees
 */
public record ToolCallExecutionError(String toolCallId, String toolName, String error) implements Error {
}
