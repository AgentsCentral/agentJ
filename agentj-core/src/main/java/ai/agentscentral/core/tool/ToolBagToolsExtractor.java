package ai.agentscentral.core.tool;

import ai.agentscentral.core.annotation.InterruptParam;
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
        final Parameter[] parameters = method.getParameters() != null ? method.getParameters() : new Parameter[]{};

        final List<ToolParameter> toolParameters = extractToolParameters(parameters);
        final List<InterruptParameter> interruptParameters = extractInterruptParameters(parameters);

        final List<ToolInterrupt> interruptsBefore = extractToolInterrupts(toolAnnotation.interruptsBefore(),
                interruptParameters);

        return new ToolCall(toolBag, method, toolAnnotation.name(),
                toolAnnotation.description(), toolParameters, interruptParameters, interruptsBefore);
    }


    private List<ToolInterrupt> extractToolInterrupts(Interrupts interrupts,
                                                      List<InterruptParameter> interruptParameters) {
        if (Objects.isNull(interrupts) || Objects.isNull(interrupts.value())) {
            return List.of();
        }

        final Map<String, InterruptParameter> parametersByName = interruptParameters
                .stream().collect(Collectors.toMap(InterruptParameter::name, i -> i));

        return Stream.of(interrupts.value())
                .map(i -> new ToolInterrupt(i.name(), i.rendererReference(),
                        findInterruptParameters(parametersByName, i.params())))
                .toList();
    }

    private List<InterruptParameter> extractInterruptParameters(Parameter[] parameters) {
        if (Objects.isNull(parameters)) {
            return List.of();
        }

        return IntStream.range(0, parameters.length)
                .filter(i -> parameters[i].isAnnotationPresent(InterruptParam.class))
                .mapToObj(i -> extractInterruptParameter(i, parameters[i]))
                .toList();
    }

    private List<ToolParameter> extractToolParameters(Parameter[] parameters) {

        if (Objects.isNull(parameters)) {
            return List.of();
        }

        return IntStream.range(0, parameters.length)
                .filter(i -> parameters[i].isAnnotationPresent(ToolParam.class))
                .mapToObj(i -> extractToolParameter(i, parameters[i]))
                .toList();
    }

    private ToolParameter extractToolParameter(int index, Parameter parameter) {

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


    private InterruptParameter extractInterruptParameter(int index, Parameter parameter) {

        if (parameter.getType().isEnum()) {
            return extractEnumParameter(index, parameter, parameter.getAnnotation(InterruptParam.class));
        } else if (parameter.getType().isPrimitive()) {
            return extractPrimitiveParameter(index, parameter, parameter.getAnnotation(InterruptParam.class));
        } else if (parameter.getType().isArray()) {
            return extractArrayParameter(index, parameter, parameter.getAnnotation(InterruptParam.class));
        } else if (Collection.class.isAssignableFrom(parameter.getType())) {
            return extractCollectionParameter(index, parameter, parameter.getAnnotation(InterruptParam.class));
        }
        return extractTypedParameter(index, parameter, parameter.getAnnotation(InterruptParam.class));
    }

    private List<InterruptParameter> findInterruptParameters(Map<String, InterruptParameter> parameters,
                                                             String... paramNames) {
        if (Objects.isNull(paramNames)) {
            return List.of();
        }

        return Stream.of(paramNames).map(parameters::get).toList();
    }

    public static ToolBagToolsExtractor getInstance() {
        return instance;
    }


}
