package ai.agentscentral.core.tool;

import ai.agentscentral.core.annotation.InterruptParameters;
import ai.agentscentral.core.annotation.Interrupts;
import ai.agentscentral.core.annotation.Tool;
import ai.agentscentral.core.annotation.ToolParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static ai.agentscentral.core.tool.ReflectionUtil.*;
import static java.util.Objects.isNull;

/**
 * ToolBagToolsExtractor
 *
 * @author Rizwan Idrees
 */
public class ToolBagToolsExtractor implements ToolsExtractor {

    private static final ToolBagToolsExtractor instance = new ToolBagToolsExtractor();

    private final Predicate<Method> toolMethods = method -> method.isAnnotationPresent(Tool.class);

    private ToolBagToolsExtractor() {

    }

    public Map<String, ToolCall> extractTools(List<ToolBag> toolBags) {

        if (isNull(toolBags) || toolBags.isEmpty()) {
            return Map.of();
        }

        final Map<String, ToolCall> tools = new HashMap<>();
        toolBags.forEach(tb -> tools.putAll(extractTools(tb)));

        return tools;
    }

    private Map<String, ToolCall> extractTools(ToolBag toolBag) {
        final Method[] declaredMethods = Optional.of(toolBag.getClass())
                .map(Class::getDeclaredMethods).orElseGet(() -> new Method[]{});


        return Stream.of(declaredMethods).filter(this.toolMethods)
                .map(m -> extractTool(toolBag, m)).collect(Collectors.toMap(ToolCall::name, toolCall -> toolCall));
    }


    private ToolCall extractTool(ToolBag toolBag, Method method) {
        final Tool toolAnnotation = method.getAnnotation(Tool.class);
        final List<ToolInterrupt> interruptsBefore = extractToolInterrupts(toolAnnotation.interruptsBefore());
        final Parameter[] parameters = method.getParameters() != null ? method.getParameters() : new Parameter[]{};

        final List<ToolParameter> toolParameters = extractParameters(parameters);

        return new ToolCall(toolBag, method, toolAnnotation.name(),
                toolAnnotation.description(), toolParameters, interruptsBefore);
    }


    private List<ToolInterrupt> extractToolInterrupts(Interrupts interrupts) {
        if (Objects.isNull(interrupts) || Objects.isNull(interrupts.value())) {
            return List.of();
        }

        return Stream.of(interrupts.value())
                .map(i -> new ToolInterrupt(i.type(), i.rendererReference(), extractInterruptParameters(i.parameters())))
                .toList();
    }

    private Map<String, String> extractInterruptParameters(InterruptParameters[] parameters) {
        if (Objects.isNull(parameters)) {
            return Map.of();
        }

        return Stream.of(parameters)
                .collect(Collectors.toMap(InterruptParameters::name, InterruptParameters::value));
    }

    private List<ToolParameter> extractParameters(Parameter[] parameters) {
        return IntStream.range(0, parameters.length)
                .mapToObj(i -> extractParameter(i, parameters[i]))
                .toList();
    }

    private ToolParameter extractParameter(int index, Parameter parameter) {

        if (parameter.getType().isEnum()) {
            return extractEnumParameter(index, parameter, parameter.getAnnotation(ToolParam.class));
        } else if (parameter.getType().isPrimitive()) {
            return extractPrimitiveParameter(index, parameter, parameter.getAnnotation(ToolParam.class));
        } else if (parameter.getType().isArray()) {
            return extractArrayParameter(index, parameter, parameter.getAnnotation(ToolParam.class));
        } else if (Collection.class.isAssignableFrom(parameter.getType())) {
            return extractCollectionParameter(index, parameter, parameter.getAnnotation(ToolParam.class));
        }
        return extractTypedParameter(index, parameter, parameter.getAnnotation(ToolParam.class));
    }

    public static ToolBagToolsExtractor getInstance() {
        return instance;
    }


}
