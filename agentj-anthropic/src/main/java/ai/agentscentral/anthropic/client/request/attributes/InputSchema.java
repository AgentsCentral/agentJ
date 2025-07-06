package ai.agentscentral.anthropic.client.request.attributes;

import java.util.Map;
import java.util.Set;

/**
 * InputSchema
 *
 * @param type
 * @param properties
 * @param required
 * @author Rizwan Idrees
 */
public record InputSchema(String type,
                          Map<String, ? extends SchemaProperty> properties,
                          Set<String> required) {
}
