package ai.agentscentral.openai.client.request.parameters;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * OpenAIToolMessage
 *
 * @param role
 * @param toolCallId
 * @param content
 * @author Rizwan Idrees
 */
public record OpenAIToolMessage(String role,
                                @JsonProperty("tool_call_id") String toolCallId,
                                @JsonUnwrapped MessageContent content) implements OpenAIMessage {

    public static final String TOOL = "tool";

    @Override
    public String role() {
        return TOOL;
    }
}
