package ai.agentscentral.anthropic.client.response;

import ai.agentscentral.anthropic.client.common.Role;
import ai.agentscentral.anthropic.client.response.attributes.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * MessagesResponse
 *
 * @param id
 * @param type
 * @param role
 * @param content
 * @param model
 * @param stopReason
 * @param stopSequence
 * @param container
 * @param usage
 * @author Rizwan Idrees
 */
public record MessagesResponse(String id,
                               MessageResponseType type,
                               Role role,
                               List<ResponseContent> content,
                               String model,
                               @JsonProperty("stop_reason") StopReason stopReason,
                               @JsonProperty("stop_sequence") String stopSequence,
                               Container container,
                               Usage usage) {
    public boolean hasToolCalls() {
        return content.stream().anyMatch(c -> c instanceof ToolUseResponseContent);
    }
}
