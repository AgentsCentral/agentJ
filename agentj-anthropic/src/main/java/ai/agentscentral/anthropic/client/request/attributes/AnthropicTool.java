package ai.agentscentral.anthropic.client.request.attributes;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * AnthropicTool
 *
 * @param name
 * @param description
 * @author Rizwan Idrees
 */
public record AnthropicTool(String name,
                            String description,
                            String type,
                            @JsonProperty("input_schema") InputSchema inputSchema) {

}
