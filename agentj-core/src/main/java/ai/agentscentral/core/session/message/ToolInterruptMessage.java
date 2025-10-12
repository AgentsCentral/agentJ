package ai.agentscentral.core.session.message;

/**
 * ToolInterruptMessage
 *
 * @param contextId
 * @param messageId
 * @param parts
 * @param timestamp
 * @author Rizwan Idrees
 */
public record ToolInterruptMessage(String contextId,
                                   String messageId,
                                   MessagePart[] parts,
                                   long timestamp) implements Message {

}
