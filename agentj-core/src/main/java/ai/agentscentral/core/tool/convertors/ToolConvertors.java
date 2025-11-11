package ai.agentscentral.core.tool.convertors;

import ai.agentscentral.core.convertors.TriConvertor;
import ai.agentscentral.core.session.message.MessagePartType;
import ai.agentscentral.core.session.message.TextPart;
import ai.agentscentral.core.session.message.ToolMessage;
import ai.agentscentral.core.tool.ToolCallExecutionError;
import ai.agentscentral.core.tool.ToolCallResult;

import static java.lang.System.currentTimeMillis;

/**
 * ToolConvertors
 *
 * @author Rizwan Idrees
 */
public class ToolConvertors {

    public static final TriConvertor<String, String, ToolCallResult, ToolMessage> convertToolCallResultToToolMessage =
            (contextId, messageId, result) -> new ToolMessage(contextId, messageId,
                    result.instruction().id(), result.instruction().name(), textParts(result) , currentTimeMillis());

    public static final TriConvertor<String, String, ToolCallExecutionError, ToolMessage> convertToolCallExecutionErrorToToolMessage =
            (contextId, messageId, error) -> new ToolMessage(contextId, messageId,
                    error.toolCallId(), error.toolName(), textParts(error) , currentTimeMillis());


    public static TextPart[] textParts(ToolCallResult result){
        return new TextPart[]{new TextPart(MessagePartType.text, result.result().toString())};
    }

    public static TextPart[] textParts(ToolCallExecutionError error){
        return new TextPart[]{new TextPart(MessagePartType.text, error.error())};
    }

}
