package ai.agentscentral.anthropic.client.request.attributes;

import ai.agentscentral.anthropic.client.common.Role;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * AnthropicUserMessage
 *
 * @param role
 * @param content
 * @author Rizwan Idrees
 */
public record AnthropicUserMessage(Role role,
                                   @JsonUnwrapped MessageContent content) implements AnthropicMessage {


    public Role role() {
        return Role.user;
    }

}
