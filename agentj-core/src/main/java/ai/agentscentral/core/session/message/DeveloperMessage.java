package ai.agentscentral.core.session.message;

/**
 * DeveloperMessage
 * @param contextId
 * @param messageId
 * @param parts
 * @param timestamp
 *
 * @author Rizwan Idrees
 */
public record DeveloperMessage(String contextId,
                               String messageId,
                               MessageType type,
                               MessagePart[] parts,
                               long timestamp) implements Message {

    public DeveloperMessage(String contextId,
                             String messageId,
                             MessagePart[] parts,
                             long timestamp) {
        this(contextId, messageId, MessageType.DEVELOPER_MESSAGE, parts, timestamp);
    }

    @Override
    public MessageType type() {
        return MessageType.DEVELOPER_MESSAGE;
    }
}
