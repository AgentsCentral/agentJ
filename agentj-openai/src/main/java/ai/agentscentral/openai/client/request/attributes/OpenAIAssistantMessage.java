package ai.agentscentral.openai.client.request.attributes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;

/**
 * {@link OpenAIMessage} with role {@value #ASSISTANT}, representing a previous
 * assistant turn in the conversation history.
 *
 * @param role      always {@value #ASSISTANT}
 * @param content   the assistant's text output; unwrapped into the enclosing JSON object;
 *                  may be {@code null} when the turn contained only tool calls
 * @param name      optional assistant display name
 * @param refusal   refusal text if the model declined; {@code null} otherwise
 * @param toolCalls {@code tool_calls} — tool invocations made during this assistant turn
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
