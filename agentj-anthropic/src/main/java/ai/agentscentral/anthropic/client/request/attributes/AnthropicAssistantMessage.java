package ai.agentscentral.anthropic.client.request.attributes;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public record AnthropicAssistantMessage(String role,
                                        @JsonUnwrapped MessageContent content) implements AnthropicMessage {

    public static final String ASSISTANT = "assistant";

    public String role() {
        return ASSISTANT;
    }
}
