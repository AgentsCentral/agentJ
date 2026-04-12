package ai.agentscentral.openai.client.response.parameters;

/**
 * The function details within a response-side {@link ToolCall}.
 *
 * @param name      name of the tool function the model wants to call
 * @param arguments JSON-encoded string of arguments to pass to the function
 *
 * @author Rizwan Idrees
 */
public record Function(String name, String arguments) {
}
