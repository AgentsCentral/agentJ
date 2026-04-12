package ai.agentscentral.anthropic.client.request.attributes;

/**
 * {@link SystemPrompt} implementation that carries a single plain-text instruction.
 *
 * <p>Used when the agent has no instructors; the default fallback text
 * {@code "You are a helpful assistant"} is wrapped in this record.</p>
 *
 * @param system the plain-text system instruction string
 *
 * @author Rizwan Idrees
 */
public record TextSystemPrompt(String system) implements SystemPrompt {
}
