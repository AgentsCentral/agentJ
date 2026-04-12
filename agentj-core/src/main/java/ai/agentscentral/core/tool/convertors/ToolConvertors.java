package ai.agentscentral.core.tool.convertors;

import ai.agentscentral.core.convertors.TriConvertor;
import ai.agentscentral.core.session.message.MessagePartType;
import ai.agentscentral.core.session.message.TextPart;
import ai.agentscentral.core.session.message.ToolMessage;
import ai.agentscentral.core.tool.ToolCallExecutionError;
import ai.agentscentral.core.tool.ToolCallResult;

import static java.lang.System.currentTimeMillis;

/**
 * Pre-built {@link TriConvertor} constants for converting tool execution outcomes into
 * {@link ToolMessage}s, used as callbacks in
 * {@link ai.agentscentral.core.tool.ResultOrError#onResult} and
 * {@link ai.agentscentral.core.tool.ResultOrError#orOnError}.
 *
 * <p>This is a utility class and cannot be instantiated.</p>
 *
 * @author Rizwan Idrees
 */
public class ToolConvertors {

    private ToolConvertors(){
    }

    /**
     * Converts a successful {@link ToolCallResult} into a {@link ToolMessage}.
     *
     * <p>The message carries the call id, tool name, and a single {@link TextPart}
     * containing the string representation of the result value.</p>
     */
    public static final TriConvertor<String, String, ToolCallResult, ToolMessage> convertToolCallResultToToolMessage =
            (contextId, messageId, result) -> new ToolMessage(contextId, messageId,
                    result.instruction().id(), result.instruction().name(), textParts(result) , currentTimeMillis());

    /**
     * Converts a {@link ToolCallExecutionError} into a {@link ToolMessage}.
     *
     * <p>The message carries the call id, tool name, and a single {@link TextPart}
     * containing the error description, allowing the model to receive and reason about
     * the failure.</p>
     */
    public static final TriConvertor<String, String, ToolCallExecutionError, ToolMessage> convertToolCallExecutionErrorToToolMessage =
            (contextId, messageId, error) -> new ToolMessage(contextId, messageId,
                    error.toolCallId(), error.toolName(), textParts(error) , currentTimeMillis());

    /**
     * Wraps the string representation of a {@link ToolCallResult}'s value in a single
     * {@link TextPart} array.
     *
     * @param result the successful tool result
     * @return a one-element array containing a {@link TextPart} with the result text
     */
    public static TextPart[] textParts(ToolCallResult result){
        return new TextPart[]{new TextPart(MessagePartType.text, result.result().toString())};
    }

    /**
     * Wraps the error description of a {@link ToolCallExecutionError} in a single
     * {@link TextPart} array.
     *
     * @param error the tool execution error
     * @return a one-element array containing a {@link TextPart} with the error text
     */
    public static TextPart[] textParts(ToolCallExecutionError error){
        return new TextPart[]{new TextPart(MessagePartType.text, error.error())};
    }

}
