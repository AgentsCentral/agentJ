package ai.agentscentral.anthropic.client.request.attributes;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * AnthropicUserMessage
 *
 * @param role
 * @param content
 * @author Rizwan Idrees
 */
public record AnthropicUserMessage(String role,
                                   @JsonUnwrapped MessageContent content) implements AnthropicMessage {

    public static final String USER = "user";

    public String role() {
        return USER;
    }

}
