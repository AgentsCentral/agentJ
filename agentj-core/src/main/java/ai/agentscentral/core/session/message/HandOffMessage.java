package ai.agentscentral.core.session.message;

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
                             MessageType type,
                             String handOffId,
                             String agentName,
                             MessagePart[] parts,
                             long timestamp) implements Message {

    public HandOffMessage(String contextId,
                          String messageId,
                          String handOffId,
                          String agentName,
                          MessagePart[] parts,
                          long timestamp) {
        this(contextId, messageId, MessageType.HANDOFF_MESSAGE, handOffId, agentName, parts, timestamp);
    }

    @Override
    public MessageType type() {
        return MessageType.HANDOFF_MESSAGE;
    }
}
