package ai.agentscentral.core.session.message;

/**
 * UserMessage
 *
 * @param contextId
 * @param messageId
 * @param parts
 * @param timestamp
 *
 * @author Rizwan Idrees
 */
public record UserMessage(String contextId,
                          String messageId,
                          MessageType type,
                          MessagePart[] parts,
                          long timestamp) implements Message {

    public UserMessage(String contextId,
                       String messageId,
                       MessagePart[] parts,
                       long timestamp) {
        this(contextId, messageId, MessageType.USER_MESSAGE, parts, timestamp);
    }

    @Override
    public MessageType type() {
        return MessageType.USER_MESSAGE;
    }
}
