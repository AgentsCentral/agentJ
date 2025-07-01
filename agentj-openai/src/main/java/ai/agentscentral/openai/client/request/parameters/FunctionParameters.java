package ai.agentscentral.openai.client.request.parameters;

import java.util.Map;
import java.util.Set;

/**
 * FunctionParameters
 *
 * @param type
 * @param properties
 * @param required
 * @author Rizwan Idrees
 */
public record FunctionParameters(String type,
                                 Map<String, ? extends FunctionProperty> properties,
                                 Set<String> required) {
}
