package ai.agentscentral.core.session.message;

import java.util.Map;

/**
 * UserInterruptMessage
 *
 * @param contextId
 * @param messageId
 * @param interruptParameterValues
 * @param parts
 * @param timestamp
 * @author Rizwan Idrees
 */
public record UserInterruptMessage(String contextId,
                                   String messageId,
                                   Map<String, String> interruptParameterValues,
                                   MessagePart[] parts,
                                   long timestamp) implements Message {

}
