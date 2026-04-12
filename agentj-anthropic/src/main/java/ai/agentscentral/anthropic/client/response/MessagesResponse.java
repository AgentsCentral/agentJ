package ai.agentscentral.anthropic.client.response;

import ai.agentscentral.anthropic.client.common.Role;
import ai.agentscentral.anthropic.client.response.attributes.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Wire-format record representing the Anthropic Messages API response.
 *
 * <p>Deserialised from the JSON response body by
 * {@link ai.agentscentral.anthropic.client.Jsonify}.  The {@code content} list is
 * polymorphically decoded via Jackson's {@code @JsonSubTypes} on
 * {@link ResponseContent}: text, thinking, redacted-thinking, tool-use, and server-tool-use
 * blocks each deserialise to their corresponding record type.</p>
 *
 * @param id            the unique message identifier assigned by Anthropic
 * @param type          the response type; currently always {@link MessageResponseType#message}
 * @param role          the role of this message; always {@link ai.agentscentral.anthropic.client.common.Role#assistant}
 * @param content       the ordered list of content blocks produced by the model
 * @param model         the model identifier that generated the response
 * @param stopReason    the reason generation stopped (e.g. {@link StopReason#end_turn},
 *                      {@link StopReason#tool_use})
 * @param stopDetails   optional additional stop detail string; mapped from
 *                      {@code stop_details}
 * @param stopSequence  the stop sequence that triggered termination, if any; mapped from
 *                      {@code stop_sequence}
 * @param container     optional container metadata for code-execution responses
 * @param usage         token usage statistics for the request and response
 *
 * @author Rizwan Idrees
 */
public record MessagesResponse(String id,
                               MessageResponseType type,
                               Role role,
                               List<ResponseContent> content,
                               String model,
                               @JsonProperty("stop_reason") StopReason stopReason,
                               @JsonProperty("stop_details") String stopDetails,
                               @JsonProperty("stop_sequence") String stopSequence,
                               Container container,
                               Usage usage) {
    /**
     * Returns {@code true} if any content block in this response is a
     * {@link ToolUseResponseContent}, indicating that the model wants to invoke a tool
     * or perform a handoff.
     *
     * @return {@code true} if at least one tool-use block is present
     */
    public boolean hasToolCalls() {
        return content.stream().anyMatch(c -> c instanceof ToolUseResponseContent);
    }
}
