package ai.agentscentral.core.session.message;

import java.util.List;
import java.util.Map;

/**
 * ToolInterruptPart
 *
 * @param type
 * @param toolCallId
 * @param interruptName
 * @param renderer
 * @param toolCallParameters
 * @param interruptParameters
 * @author Rizwan Idrees
 */
public record ToolInterruptPart(MessagePartType type,
                                String toolCallId,
                                String interruptName,
                                String renderer,
                                Map<String, Object> toolCallParameters,
                                List<ToolInterruptParameter> interruptParameters) implements MessagePart {

    public MessagePartType type() {
        return MessagePartType.tool_interrupt;
    }
}
