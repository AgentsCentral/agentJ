package ai.agentscentral.anthropic.client.response.attributes;

import java.util.Map;

/**
 * ToolUseResponseContent
 *
 * @param id
 * @param type
 * @param name
 * @param input
 * @param caller
 * @author Rizwan Idrees
 */
public record ToolUseResponseContent(String id,
                                     ResponseContentType type,
                                     String name,
                                     Map<String, Object> input,
                                     ToolUseCaller caller) implements ResponseContent {
}
