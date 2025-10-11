package ai.agentscentral.core.session.message;

import java.util.Map;

/**
 * InterruptMessage
 *
 * @param contextId
 * @param messageId
 * @param type
 * @param renderer
 * @param interruptParameters
 * @param toolCallParameters
 * @param parts
 * @param timestamp
 * @author Rizwan Idrees
 */
public record InterruptMessage(String contextId,
                               String messageId,
                               InterruptType type,
                               String renderer,
                               Map<String, String> interruptParameters,
                               Map<String, Object> toolCallParameters,
                               MessagePart[] parts,
                               long timestamp) implements Message {

}
