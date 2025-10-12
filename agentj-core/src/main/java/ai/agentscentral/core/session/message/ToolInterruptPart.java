package ai.agentscentral.core.session.message;

import java.util.List;
import java.util.Map;

/**
 * InterruptPart
 *
 * @param type
 * @param toolCallId
 * @param renderer
 * @param toolCallParameters
 * @param interruptParameters
 * @author Rizwan Idrees
 */
public record ToolInterruptPart(MessagePartType type,
                                String toolCallId,
                                String renderer,
                                Map<String, Object> toolCallParameters,
                                List<ToolInterruptParameter> interruptParameters) implements MessagePart {

    public MessagePartType type() {
        return MessagePartType.interrupt;
    }
}
