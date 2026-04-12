package ai.agentscentral.openai.client.response.parameters;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

/**
 * The assistant message returned within a {@link Choice}.
 *
 * @param content     text content of the assistant message; {@code null} when the model
 *                    returned only tool calls
 * @param refusal     refusal text if the model declined to respond; {@code null}
 *                    otherwise
 * @param role        always {@code "assistant"} for completion responses
 * @param annotations citations or other structured annotations on the content
 * @param toolCalls   {@code tool_calls} — list of tool invocations requested by the
 *                    model; {@code null} or empty when no tools were called
 *
 * @author Rizwan Idrees
 */
public record ChoiceMessage(String content,
                            String refusal,
                            String role,
                            List<Annotation> annotations,
                            @JsonProperty("tool_calls") List<ToolCall> toolCalls) {

    /**
     * Returns {@code true} if the model requested at least one tool call.
     *
     * @return {@code true} when {@code toolCalls} is non-null and non-empty
     */
    public boolean hasToolCalls() {
        return Objects.nonNull(toolCalls) && !toolCalls.isEmpty();
    }

}
