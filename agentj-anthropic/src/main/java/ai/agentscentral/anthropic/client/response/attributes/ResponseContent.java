package ai.agentscentral.anthropic.client.response.attributes;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * ResponseContent
 * @author Rizwan Idrees
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TextResponseContent.class, name = "text"),
        @JsonSubTypes.Type(value = ThinkingResponseContent.class, name = "thinking"),
        @JsonSubTypes.Type(value = RedactedThinkingResponseContent.class, name = "redacted_thinking"),
        @JsonSubTypes.Type(value = ToolUseResponseContent.class, name = "tool_use"),
        @JsonSubTypes.Type(value = ServerToolUseResponseContent.class, name = "server_tool_use"),
        @JsonSubTypes.Type(value = ServerToolUseResponseContent.class, name = "web_search_tool_result")
})
public interface ResponseContent {

    ResponseContentType type();

}
