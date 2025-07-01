package ai.agentscentral.openai.client.request.parameters;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;

/**
 * OpenAIAssistantMessage
 * @param role
 * @param content
 * @param name
 * @param refusal
 * @param toolCalls
 *
 * @author Rizwan Idrees
 */
public record OpenAIAssistantMessage(String role,
                                     @JsonUnwrapped MessageContent content,
                                     String name,
                                     String refusal,
                                     @JsonProperty("tool_calls") List<OpenAIToolCall> toolCalls) implements OpenAIMessage {

    public static final String ASSISTANT = "assistant";

    @Override
    public String role() {
        return ASSISTANT;
    }
}
