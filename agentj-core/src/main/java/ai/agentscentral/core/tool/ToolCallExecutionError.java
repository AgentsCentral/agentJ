package ai.agentscentral.core.tool;

import ai.agentscentral.core.error.Error;

/**
 * ToolCallExecutionError
 *
 * @param toolCallId
 * @param toolName
 * @param error
 *
 * @author Rizwan Idrees
 */
public record ToolCallExecutionError(String toolCallId, String toolName, String error) implements Error {
}
