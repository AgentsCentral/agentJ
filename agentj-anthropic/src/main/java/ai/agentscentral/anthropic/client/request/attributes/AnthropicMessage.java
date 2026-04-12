package ai.agentscentral.anthropic.client.request.attributes;

import ai.agentscentral.anthropic.client.common.Role;

/**
 * Common interface for messages sent in an Anthropic Messages request.
 *
 * <p>Concrete implementations:
 * <ul>
 *   <li>{@link AnthropicUserMessage} — a message with {@link Role#user}</li>
 *   <li>{@link AnthropicAssistantMessage} — a message with {@link Role#assistant}</li>
 * </ul>
 *
 * @author Rizwan Idrees
 */
public interface AnthropicMessage {

    /**
     * Returns the conversation role of this message.
     *
     * @return {@link Role#user} or {@link Role#assistant}
     */
    Role role();
}
