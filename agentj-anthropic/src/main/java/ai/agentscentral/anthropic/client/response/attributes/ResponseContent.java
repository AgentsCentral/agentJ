package ai.agentscentral.anthropic.client.response.attributes;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Polymorphic base interface for a single content block in an Anthropic
 * {@link ai.agentscentral.anthropic.client.response.MessagesResponse}.
 *
 * <p>Jackson uses the {@code type} field to deserialise each block into one of the
 * following subtypes:
 * <ul>
 *   <li>{@link TextResponseContent} — a plain-text output block ({@code "text"})</li>
 *   <li>{@link ThinkingResponseContent} — an extended-thinking block
 *       ({@code "thinking"})</li>
 *   <li>{@link RedactedThinkingResponseContent} — a redacted thinking block
 *       ({@code "redacted_thinking"})</li>
 *   <li>{@link ToolUseResponseContent} — a tool or handoff invocation
 *       ({@code "tool_use"})</li>
 *   <li>{@link ServerToolUseResponseContent} — a server-side tool invocation such as
 *       web search ({@code "server_tool_use"}, {@code "web_search_tool_result"})</li>
 * </ul>
 *
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

    /**
     * Returns the content-block type discriminator used by Jackson for polymorphic
     * deserialisation.
     *
     * @return the {@link ResponseContentType} of this block
     */
    ResponseContentType type();

}
