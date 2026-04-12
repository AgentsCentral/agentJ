package ai.agentscentral.openai.client.request.attributes;

import java.util.Map;
import java.util.Set;

/**
 * JSON-schema-style parameter definition for a {@link ToolFunction}, describing the
 * object structure of the function's arguments.
 *
 * @param type       always {@code "object"} per the OpenAI function-calling schema
 * @param properties map of parameter name → {@link FunctionProperty} schema
 * @param required   set of parameter names that must be present in the model's response
 *
 * @author Rizwan Idrees
 */
public record FunctionParameters(String type,
                                 Map<String, ? extends FunctionProperty> properties,
                                 Set<String> required) {
}
