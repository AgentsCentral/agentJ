package ai.agentscentral.openai.processor;

import ai.agentscentral.core.handoff.Handoff;
import ai.agentscentral.core.handoff.HandoffInstruction;
import ai.agentscentral.core.tool.EnumToolParameter;
import ai.agentscentral.core.tool.ToolCall;
import ai.agentscentral.core.tool.ToolCallInstruction;
import ai.agentscentral.core.tool.ToolParameter;
import ai.agentscentral.openai.client.request.attributes.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;

import java.util.*;

import static ai.agentscentral.openai.client.request.attributes.OpenAITool.FUNCTION;
import static java.util.stream.Collectors.toSet;

/**
 * OpenAIToolConvertor
 *
 * @author Rizwan Idrees
 */
class OpenAIToolConvertor {


    private static final ObjectMapper mapper = new ObjectMapper();
    private static final JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(mapper);

    static Map<String, OpenAITool> toolsToOpenAITools(Map<String, ToolCall> tools) {

        if (Objects.isNull(tools) || tools.isEmpty()) {
            return null;
        }

        final Map<String, OpenAITool> openAIToolMap = new HashMap<>();

        tools.forEach((key, value) -> openAIToolMap.put(key, toOpenAITool(value)));

        return openAIToolMap;
    }


    static Map<String, OpenAITool> handOffsToOpenAITools(Map<String, Handoff> handOffs) {

        if (Objects.isNull(handOffs) || handOffs.isEmpty()) {
            return null;
        }

        final Map<String, OpenAITool> openAIHandoffMap = new HashMap<>();

        handOffs.forEach((key, value) -> openAIHandoffMap.put(key, toOpenAITool(value)));

        return openAIHandoffMap;
    }

    private static OpenAITool toOpenAITool(ToolCall toolCall) {
        final ToolFunction function = new ToolFunction(toolCall.name(), toolCall.description(), toFunctionParameters(toolCall.parameters()), false);
        return new OpenAITool(FUNCTION, function);
    }

    private static OpenAITool toOpenAITool(Handoff handoff) {
        final ToolFunction function = new ToolFunction(handoff.id(), handoff.description(), null, false);
        return new OpenAITool(FUNCTION, function);
    }

    private static FunctionParameters toFunctionParameters(List<ToolParameter> parameters) {

        if (Objects.isNull(parameters)) {
            return null;
        }

        Set<String> requiredParameters = parameters.stream().filter(ToolParameter::required)
                .map(ToolParameter::name)
                .collect(toSet());

        return new FunctionParameters("object", functionProperties(parameters), requiredParameters);
    }

    private static Map<String, ? extends FunctionProperty> functionProperties(List<ToolParameter> parameters) {
        final Map<String, FunctionProperty> properties = new HashMap<>();
        parameters.forEach(p -> properties.put(p.name(), functionProperty(p)));
        return properties;
    }

    private static FunctionProperty functionProperty(ToolParameter parameter) {

        final String jsonType = javaTypeToJsonType(parameter.type());

        if (parameter instanceof EnumToolParameter enumParameter) {
            return new EnumFunctionProperty(jsonType, enumParameter.enumValues().toArray(String[]::new));
        }

        return new TypedFunctionProperty(jsonType, parameter.description());
    }

    private static String javaTypeToJsonType(Class<?> type) {
        try {
            final JsonSchema jsonSchema = jsonSchemaGenerator.generateSchema(type);
            return jsonSchema.getType().value();
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unable to process tool parameter type " + type);
        }
    }

    static OpenAIToolCall toOpenAIToolCall(ToolCallInstruction toolCallInstruction) {
        final String id = toolCallInstruction.id();
        final String name = toolCallInstruction.toolCall().name();
        final String arguments = toolCallInstruction.rawArguments();
        return new OpenAIToolCall(id, FUNCTION, new ToolCallFunction(name, arguments));
    }

    static OpenAIToolCall toOpenAIToolCall(HandoffInstruction handoffInstruction) {
        final String id = handoffInstruction.callId();
        final String name = handoffInstruction.handoff().id();
        return new OpenAIToolCall(id, FUNCTION, new ToolCallFunction(name, "{}"));
    }
}
