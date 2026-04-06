package ai.agentscentral.anthropic.client.response.attributes;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * ToolUseServerCaller
 *
 * @param type
 * @param toolId
 * @author Rizwan Idrees
 */
public record ToolUseServerCaller(String type, @JsonProperty("tool_id") String toolId) implements ToolUseCaller {

}
