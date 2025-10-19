package ai.agentscentral.core.tool;

import ai.agentscentral.core.session.message.InterruptParameterValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import static ai.agentscentral.core.tool.ResultOrError.ofError;
import static ai.agentscentral.core.tool.ResultOrError.ofResult;
import static java.util.stream.Collectors.toMap;

/**
 * DefaultToolExecutor
 *
 * @author Rizwan Idrees
 */
public class DefaultToolCallExecutor<T> implements ToolCallExecutor<T> {

    private static final Logger logger = LoggerFactory.getLogger(DefaultToolCallExecutor.class);

    @Override
    public ResultOrError<ToolCallResult, ToolCallExecutionError, T> execute(ToolCallInstruction instruction,
                                                                            List<InterruptParameterValue> interruptParameters) {
        final String toolCallId = instruction.id();
        final ToolCall toolCall = instruction.toolCall();
        try {
            final Method method = toolCall.method();
            method.setAccessible(true);

            final Object result = method.invoke(toolCall.toolBag(), parameters(instruction, interruptParameters));
            return ofResult(new DefaultToolCallResult(instruction, result, method.getReturnType()));

        } catch (IllegalAccessException e) {
            logger.error("Failed to execute tool due to method access level. Tool info {}", instruction.toolCall());
            return ofError(new ToolCallExecutionError(toolCallId, toolCall.name(), "Unable to access the tool " + toolCall.name()));
        } catch (InvocationTargetException e) {
            logger.error("Failed to execute tool due to method invocation error. Tool info {}", instruction.toolCall());
            return ofError(new ToolCallExecutionError(toolCallId, toolCall.name(),
                    "Unable to call the tool " + toolCall.name()));
        } catch (Exception e) {
            logger.error("Failed to execute tool due to error. Tool info {}, error {}", instruction.toolCall(),
                    e.getMessage());
            return ofError(new ToolCallExecutionError(toolCallId, toolCall.name(), "Error calling the tool " + toolCall.name()));
        }
    }

    private Object[] parameters(ToolCallInstruction instruction, List<InterruptParameterValue> interruptParameters) {
        final ToolCall toolCall = instruction.toolCall();

        if (Objects.isNull(toolCall.parameters()) && Objects.isNull(interruptParameters)) {
            return null;
        }

        final List<MethodParameter> methodParameters = allMethodParameters(instruction, interruptParameters);

        final Object[] parameters = new Object[methodParameters.size()];

        methodParameters.sort(Comparator.comparing(MethodParameter::index));

        for (int i = 0; i < methodParameters.size(); i++) {
            parameters[i] = methodParameters.get(i).value();
        }

        return parameters;
    }

    private List<MethodParameter> allMethodParameters(ToolCallInstruction instruction,
                                                      List<InterruptParameterValue> interruptParameterValues) {

        final List<MethodParameter> methodParameters = new ArrayList<>();

        final Map<String, Object> properties = instruction.arguments();
        final Map<String, Object> interruptValues = interruptParameterValues.stream()
                .collect(toMap(InterruptParameterValue::name, InterruptParameterValue::value));

        final ToolCall toolCall = instruction.toolCall();

        final List<ToolParameter> toolParameters = Objects.isNull(toolCall.parameters()) ?
                List.of() : toolCall.parameters();

        final List<MethodParameter> toolMethodParameters = toolParameters.stream()
                .map(tp -> new MethodParameter(tp.index(), tp.name(), properties.get(tp.name()))).toList();

        final List<InterruptParameter> interruptParameters = Objects.isNull(toolCall.interruptParameters()) ?
                List.of() : toolCall.interruptParameters();

        final List<MethodParameter> interruptMethodParameters = interruptParameters.stream()
                .map(ip -> new MethodParameter(ip.index(), ip.name(), interruptValues.get(ip.name()))).toList();


        methodParameters.addAll(toolMethodParameters);
        methodParameters.addAll(interruptMethodParameters);


        return methodParameters;
    }


    private record MethodParameter(int index, String name, Object value) {
    }

}
