package ai.agentscentral.anthropic.processor;

import ai.agentscentral.anthropic.client.request.attributes.*;
import ai.agentscentral.core.handoff.Handoff;
import ai.agentscentral.core.tool.EnumToolParameter;
import ai.agentscentral.core.tool.ToolCall;
import ai.agentscentral.core.tool.ToolParameter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;

import java.util.*;

import static java.util.stream.Collectors.toSet;

/**
 * AnthropicToolConvertor
 *
 * @author Rizwan Idrees
 */
class AnthropicToolConvertor {


    private static final ObjectMapper mapper = new ObjectMapper();
    private static final JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(mapper);

    static Map<String, AnthropicTool> toolsToAnthropicTool(Map<String, ToolCall> tools) {

        if (Objects.isNull(tools) || tools.isEmpty()) {
            return null;
        }

        final Map<String, AnthropicTool> openAIToolMap = new HashMap<>();

        tools.forEach((key, value) -> openAIToolMap.put(key, toAnthropicTool(value)));

        return openAIToolMap;
    }


    static Map<String, AnthropicTool> handOffsToAnthropicTool(Map<String, Handoff> handOffs) {

        if (Objects.isNull(handOffs) || handOffs.isEmpty()) {
            return null;
        }

        final Map<String, AnthropicTool> anthropicHandoffMap = new HashMap<>();

        handOffs.forEach((key, value) -> anthropicHandoffMap.put(key, toAnthropicTool(value)));

        return anthropicHandoffMap;
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

//    static AnthropicTool toOpenAIToolCall(ToolCallInstruction toolCallInstruction) {
//        final String id = toolCallInstruction.id();
//        final String name = toolCallInstruction.toolCall().name();
//        final String arguments = toolCallInstruction.rawArguments();
//        return new OpenAIToolCall(id, FUNCTION, new ToolCallFunction(name, arguments));
//    }

//    static AnthropicTool toOpenAIToolCall(HandoffInstruction handoffInstruction) {
//        final String id = handoffInstruction.callId();
//        final String name = handoffInstruction.handoff().id();
//        return new OpenAIToolCall(id, FUNCTION, new ToolCallFunction(name, "{}"));
//    }
}
