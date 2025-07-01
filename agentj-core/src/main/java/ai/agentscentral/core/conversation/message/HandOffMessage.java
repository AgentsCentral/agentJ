package ai.agentscentral.core.conversation.message;

/**
 * HandOffMessage
 *
 * @param contextId
 * @param messageId
 * @param handOffId
 * @param agentName
 * @param parts
 * @param timestamp
 *
 * @author Rizwan Idrees
 */
public record HandOffMessage(String contextId,
                             String messageId,
                             String handOffId,
                             String agentName,
                             MessagePart[] parts,
                             long timestamp) implements Message {
}
