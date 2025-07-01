package ai.agentscentral.openai.client.response.parameters;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

/**
 * ChoiceMessage
 *
 * @param content
 * @param refusal
 * @param role
 * @param annotations
 * @param toolCalls
 * @author Rizwan Idrees
 */
public record ChoiceMessage(String content,
                            String refusal,
                            String role,
                            List<Annotation> annotations,
                            @JsonProperty("tool_calls") List<ToolCall> toolCalls) {


    public boolean hasToolCalls() {
        return Objects.nonNull(toolCalls) && !toolCalls.isEmpty();
    }

}
