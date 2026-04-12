package ai.agentscentral.anthropic.client.request.attributes;

import java.util.Map;
import java.util.Set;

/**
 * JSON Schema descriptor for the input parameters of an {@link AnthropicTool}.
 *
 * <p>Serialised as the {@code input_schema} object in the Anthropic tool definition.
 * Each parameter becomes an entry in {@code properties} and required parameter names
 * are listed in the {@code required} set.</p>
 *
 * @param type       the JSON Schema type; always {@code "object"} for tool inputs
 * @param properties a map from parameter name to its {@link SchemaProperty} descriptor
 * @param required   the set of parameter names that the model must supply
 *
 * @author Rizwan Idrees
 */
public record InputSchema(String type,
                          Map<String, ? extends SchemaProperty> properties,
                          Set<String> required) {
}
