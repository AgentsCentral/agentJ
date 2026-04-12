package ai.agentscentral.anthropic.client.request.attributes;

/**
 * Marker interface for the system-prompt field of a
 * {@link ai.agentscentral.anthropic.client.request.MessagesRequest}.
 *
 * <p>The system prompt is {@link com.fasterxml.jackson.annotation.JsonUnwrapped} in the
 * request, so its fields are inlined at the root level.  Two concrete implementations
 * are provided:
 * <ul>
 *   <li>{@link TextSystemPrompt} — a single plain-text {@code "system"} string</li>
 *   <li>{@link SystemPrompts} — a list of typed {@link TextPrompt} blocks under
 *       {@code "system"}</li>
 * </ul>
 *
 * @author Rizwan Idrees
 */
public interface SystemPrompt {
}
