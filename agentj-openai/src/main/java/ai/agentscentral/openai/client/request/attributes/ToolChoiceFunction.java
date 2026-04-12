package ai.agentscentral.openai.client.request.attributes;

/**
 * Identifies the specific function to force the model to call when used with
 * {@link ToolChoiceFunctionCall}.
 *
 * @param name the name of the function to call
 *
 * @author Rizwan Idrees
 */
public record ToolChoiceFunction(String name) {
}
