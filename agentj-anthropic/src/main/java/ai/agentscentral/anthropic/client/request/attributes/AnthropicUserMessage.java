package ai.agentscentral.anthropic.client.request.attributes;

import ai.agentscentral.anthropic.client.common.Role;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * {@link AnthropicMessage} implementation for {@link Role#user} messages.
 *
 * <p>Carries a {@link MessageContent} payload that is
 * {@link com.fasterxml.jackson.annotation.JsonUnwrapped} so its fields merge directly
 * into the message object.  The {@link #role()} override always returns
 * {@link Role#user}.</p>
 *
 * @param role    the role discriminator; always overridden to {@link Role#user}
 * @param content the message content (text or tool-result parts)
 *
 * @author Rizwan Idrees
 */
public record AnthropicUserMessage(Role role,
                                   @JsonUnwrapped MessageContent content) implements AnthropicMessage {


    public Role role() {
        return Role.user;
    }

}
