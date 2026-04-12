package ai.agentscentral.anthropic.client.request.attributes;

import java.util.List;

/**
 * {@link SystemPrompt} implementation that carries an ordered list of typed
 * {@link TextPrompt} blocks.
 *
 * <p>Used when the agent has one or more
 * {@link ai.agentscentral.core.agent.instructor.Instructor}s; each instructor's output
 * becomes a separate {@link TextPrompt} entry.  The API accepts this as the structured
 * system-prompt format.</p>
 *
 * @param system the ordered list of text prompts that form the system instruction
 *
 * @author Rizwan Idrees
 */
public record SystemPrompts(List<TextPrompt> system) implements SystemPrompt {
}
