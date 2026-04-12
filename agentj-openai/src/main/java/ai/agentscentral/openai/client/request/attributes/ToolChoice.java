package ai.agentscentral.openai.client.request.attributes;

/**
 * Marker interface for the {@code tool_choice} field of a
 * {@link ai.agentscentral.openai.client.request.CompletionRequest}.
 *
 * <p>Concrete implementations:
 * <ul>
 *   <li>{@link ToolChoiceMode} — the string modes {@code "auto"} and {@code "none"}</li>
 *   <li>{@link ToolChoiceFunctionCall} — forces the model to call a specific function</li>
 * </ul>
 *
 * @author Rizwan Idrees
 */
public interface ToolChoice {
}
