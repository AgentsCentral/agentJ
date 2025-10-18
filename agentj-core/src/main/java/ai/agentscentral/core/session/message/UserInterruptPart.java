package ai.agentscentral.core.session.message;

import java.util.List;

/**
 * UserInterruptPart
 *
 * @param type
 * @param toolCallId
 * @param interruptName
 * @param interruptParameters
 * @author Rizwan Idrees
 */
public record UserInterruptPart(MessagePartType type,
                                String toolCallId,
                                String interruptName,
                                List<InterruptParameterValue> interruptParameters) implements MessagePart {

    public MessagePartType type() {
        return MessagePartType.user_interrupt;
    }
}
