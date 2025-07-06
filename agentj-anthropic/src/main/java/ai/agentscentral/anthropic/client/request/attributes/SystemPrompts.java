package ai.agentscentral.anthropic.client.request.attributes;

import java.util.List;

/**
 * TextPrompt
 *
 * @author Rizwan Idrees
 */
public record SystemPrompts(List<TextPrompt> system) implements SystemPrompt {
}
