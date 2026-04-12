package ai.agentscentral.core.session.message;

/**
 * Represents the result of a tool invocation, sent back to the LLM provider as context
 * for the next model call.
 *
 * <p>After {@link ai.agentscentral.core.tool.DefaultToolCallExecutor} executes a tool
 * method, the result (or error) is converted into a {@code ToolMessage} and persisted in
 * the context. On the next pass of the tool-call loop the provider receives these
 * messages alongside the conversation history so the model can reason about the
 * tool output.</p>
 *
 * @param contextId  the conversation context identifier
 * @param messageId  unique identifier for this message
 * @param toolCallId the id of the {@link ai.agentscentral.core.tool.ToolCallInstruction}
 *                   this result corresponds to; used by the provider to correlate the
 *                   result with the original call
 * @param toolName   the name of the tool that was invoked
 * @param parts      content parts carrying the serialised tool result
 * @param timestamp  epoch-millisecond creation timestamp
 *
 * @author Rizwan Idrees
 */
public record ToolMessage(String contextId,
                          String messageId,
                          String toolCallId,
                          String toolName,
                          MessagePart[] parts,
                          long timestamp) implements Message {
}
