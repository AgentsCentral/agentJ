package ai.agentscentral.anthropic.client.response.attributes;

import java.util.Map;

/**
 * ServerToolUseResponseContent
 *
 * @param id
 * @param type
 * @param name
 * @param input
 * @author Rizwan Idrees
 */
public record ServerToolUseResponseContent(String id,
                                           ResponseContentType type,
                                           ServerToolUseName name,
                                           Map<String, Object> input) implements ResponseContent {
}
