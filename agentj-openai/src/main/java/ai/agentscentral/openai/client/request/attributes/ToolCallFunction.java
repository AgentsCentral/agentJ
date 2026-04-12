package ai.agentscentral.openai.client.request.attributes;

/**
 * The function identifier and arguments within an {@link OpenAIToolCall}, used when
 * replaying a previous tool-call turn in the conversation history.
 *
 * @param name      name of the function that was called
 * @param arguments JSON-encoded string of the arguments that were passed to the function
 *
 * @author Rizwan Idrees
 */
public record ToolCallFunction(String name, String arguments) {
}
