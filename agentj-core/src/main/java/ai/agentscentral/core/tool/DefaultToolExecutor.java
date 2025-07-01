package ai.agentscentral.core.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static ai.agentscentral.core.tool.ResultOrError.ofError;
import static ai.agentscentral.core.tool.ResultOrError.ofResult;

/**
 * DefaultToolExecutor
 *
 * @param <T>
 *
 * @author Rizwan Idrees
 */
public class DefaultToolExecutor<T> implements ToolCallExecutor<T> {

    private static final Logger logger = LoggerFactory.getLogger(DefaultToolExecutor.class);

    @Override
    public ResultOrError<ToolCallResult, ToolCallExecutionError, T> execute(ToolCallInstruction instruction) {
        final String toolCallId = instruction.id();
        final ToolCall toolCall = instruction.toolCall();
        try {
            final Object result = toolCall.method().invoke(toolCall.toolBag(), parameters(instruction));
            return ofResult(new DefaultToolCallResult(instruction, result, toolCall.method().getReturnType()));
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

    private Object[] parameters(ToolCallInstruction instruction) {
        final ToolCall toolCall = instruction.toolCall();
        final List<ToolParameter> toolCallParameters = toolCall.parameters();

        if (Objects.isNull(toolCallParameters)) {
            return null;
        }


        final Map<String, Object> properties = instruction.arguments();
        final Object[] parameters = new Object[toolCallParameters.size()];

        toolCallParameters.sort(Comparator.comparing(ToolParameter::index));

        for (int i = 0; i < toolCallParameters.size(); i++) {
            final String name = toolCallParameters.get(i).name();
            parameters[i] = properties.get(name);
        }

        return parameters;
    }
}
