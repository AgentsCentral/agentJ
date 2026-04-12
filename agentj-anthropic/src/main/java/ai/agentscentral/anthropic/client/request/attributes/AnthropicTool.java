package ai.agentscentral.anthropic.client.request.attributes;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Wire-format descriptor for a tool or handoff pseudo-tool sent in an Anthropic
 * Messages request.
 *
 * <p>Corresponds to a single entry in the {@code tools} array of the request body.
 * AgentJ tools supply an {@link InputSchema} derived from their parameter list; handoff
 * pseudo-tools set {@code inputSchema} to {@code null}.</p>
 *
 * @param name        the tool name as it will appear in {@code tool_use} response blocks
 * @param description a human-readable description helping the model decide when to invoke
 *                    the tool
 * @param type        the tool type string sent to the API (currently {@code "custom"})
 * @param inputSchema the JSON Schema describing the tool's input parameters; {@code null}
 *                    for handoff tools; mapped to {@code input_schema}
 *
 * @author Rizwan Idrees
 */
public record AnthropicTool(String name,
                            String description,
                            String type,
                            @JsonProperty("input_schema") InputSchema inputSchema) {

}
