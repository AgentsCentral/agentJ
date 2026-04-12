package ai.agentscentral.anthropic.executor;

import ai.agentscentral.anthropic.client.request.attributes.*;
import ai.agentscentral.core.handoff.Handoff;
import ai.agentscentral.core.tool.EnumToolParameter;
import ai.agentscentral.core.tool.ToolCall;
import ai.agentscentral.core.tool.ToolParameter;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.module.jsonSchema.JsonSchema;
import tools.jackson.module.jsonSchema.JsonSchemaGenerator;

import java.util.*;

import static java.util.stream.Collectors.toSet;

/**
 * Converts AgentJ tool and handoff definitions to Anthropic
 * {@link AnthropicTool} descriptors.
 *
 * <p>Tool parameters are translated to a JSON Schema {@link InputSchema}: primitive and
 * object types are mapped via Jackson's {@link tools.jackson.module.jsonSchema.JsonSchemaGenerator};
 * enum-typed parameters produce an {@link EnumSchemaProperty} with the allowed values;
 * all other parameters produce a {@link TypedSchemaProperty}.  Required parameters are
 * collected into the schema's {@code required} set.</p>
 *
 * <p>Handoffs are represented as Anthropic tools with no input schema, because the LLM
 * signals a handoff by "calling" the handoff tool with its identifier.</p>
 *
 * <p>This is a package-private utility class and cannot be instantiated.</p>
 *
 * @author Rizwan Idrees
 */
class AnthropicToolConvertor {


    private static final ObjectMapper mapper = new ObjectMapper();
    private static final JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(mapper);

    /**
     * Converts a map of AgentJ {@link ToolCall}s to a map of {@link AnthropicTool}s,
     * preserving the tool name as the key.
     *
     * @param tools AgentJ tool definitions keyed by name; may be {@code null} or empty
     * @return a map of {@link AnthropicTool}s, or {@code null} if {@code tools} is null
     *         or empty
     */
    static Map<String, AnthropicTool> toolsToAnthropicTool(Map<String, ToolCall> tools) {

        if (Objects.isNull(tools) || tools.isEmpty()) {
            return null;
        }

        final Map<String, AnthropicTool> toolMap = new HashMap<>();

        tools.forEach((key, value) -> toolMap.put(key, toAnthropicTool(value)));

        return toolMap;
    }


    /**
     * Converts a map of AgentJ {@link Handoff}s to a map of {@link AnthropicTool}s.
     * Each handoff is represented as a tool with no input schema.
     *
     * @param handOffs AgentJ handoff definitions keyed by handoff id; may be {@code null}
     *                 or empty
     * @return a map of {@link AnthropicTool}s, or {@code null} if {@code handOffs} is
     *         null or empty
     */
    static Map<String, AnthropicTool> handOffsToAnthropicTool(Map<String, Handoff> handOffs) {

        if (Objects.isNull(handOffs) || handOffs.isEmpty()) {
            return null;
        }

        final Map<String, AnthropicTool> handoffMap = new HashMap<>();

        handOffs.forEach((key, value) -> handoffMap.put(key, toAnthropicTool(value)));

        return handoffMap;
    }

    private static AnthropicTool toAnthropicTool(ToolCall toolCall) {
        return new AnthropicTool(toolCall.name(), toolCall.description(), "custom", toInputSchema(toolCall.parameters()));
    }

    private static AnthropicTool toAnthropicTool(Handoff handoff) {
        return new AnthropicTool(handoff.id(), handoff.description(), "custom", null);
    }

    private static InputSchema toInputSchema(List<ToolParameter> parameters) {

        if (Objects.isNull(parameters)) {
            return null;
        }

        final Set<String> requiredParameters = parameters.stream().filter(ToolParameter::required)
                .map(ToolParameter::name)
                .collect(toSet());

        return new InputSchema("object", toSchemaProperties(parameters), requiredParameters);
    }

    private static Map<String, ? extends SchemaProperty> toSchemaProperties(List<ToolParameter> parameters) {
        final Map<String, SchemaProperty> properties = new HashMap<>();
        parameters.forEach(p -> properties.put(p.name(), functionProperty(p)));
        return properties;
    }

    private static SchemaProperty functionProperty(ToolParameter parameter) {

        final String jsonType = javaTypeToJsonType(parameter.type());

        if (parameter instanceof EnumToolParameter enumParameter) {
            return new EnumSchemaProperty(jsonType, enumParameter.enumValues().toArray(String[]::new));
        }

        return new TypedSchemaProperty(jsonType, parameter.description());
    }

    private static String javaTypeToJsonType(Class<?> type) {
        try {
            final JsonSchema jsonSchema = jsonSchemaGenerator.generateSchema(type);
            return jsonSchema.getType().value();
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unable to process tool parameter type " + type);
        }
    }

}
