package ai.agentscentral.anthropic.client.request.attributes;


import ai.agentscentral.anthropic.client.common.Role;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

public record AnthropicAssistantMessage(Role role,
                                        @JsonUnwrapped MessageContent content) implements AnthropicMessage {


    public Role role() {
        return Role.assistant;
    }
}
