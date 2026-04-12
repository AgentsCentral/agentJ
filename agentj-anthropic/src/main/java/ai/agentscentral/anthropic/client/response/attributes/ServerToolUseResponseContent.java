package ai.agentscentral.anthropic.client.response.attributes;

import java.util.Map;

/**
 * {@link ResponseContent} implementation for server-side tool invocation blocks
 * ({@link ResponseContentType#server_tool_use} and
 * {@link ResponseContentType#web_search_tool_result}).
 *
 * <p>Represents a built-in Anthropic server tool (such as web search or code execution)
 * invoked by the model.  Unlike user-defined tools, these are handled by the Anthropic
 * platform rather than by AgentJ's tool executor.</p>
 *
 * @param id    the unique identifier for this server-tool-use block
 * @param type  the content-block type
 * @param name  the server tool name as a {@link ServerToolUseName} enum value
 * @param input a map of argument name to value supplied by the model; may be empty
 *
 * @author Rizwan Idrees
 */
public record ServerToolUseResponseContent(String id,
                                           ResponseContentType type,
                                           ServerToolUseName name,
                                           Map<String, Object> input) implements ResponseContent {
}
