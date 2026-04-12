package ai.agentscentral.openai.client.request.attributes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * {@link OpenAIMessage} with role {@value #TOOL}, carrying the result of a tool call
 * back to the model.
 *
 * @param role       always {@value #TOOL}
 * @param toolCallId {@code tool_call_id} — the identifier from the corresponding
 *                   {@link OpenAIToolCall} that this message is responding to
 * @param content    the tool's output; unwrapped into the enclosing JSON object
 *
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
