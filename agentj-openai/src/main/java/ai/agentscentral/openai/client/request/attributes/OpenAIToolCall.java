package ai.agentscentral.openai.client.request.attributes;

/**
 * Wire record representing a tool-call entry in an {@link OpenAIAssistantMessage}, used
 * when replaying the assistant's tool-call decisions in the conversation history.
 *
 * @param id       unique tool-call identifier assigned by the model
 * @param type     always {@code "function"}
 * @param function the function name and its JSON-encoded arguments
 *
 * @author Rizwan Idrees
 */
public record OpenAIToolCall(String id, String type, ToolCallFunction function) {
}
