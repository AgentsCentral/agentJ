package ai.agentscentral.messaging.message;

/**
 * TextMessagePart
 *
 * @param type
 * @param text
 * @author Rizwan Idrees
 */
public record TextMessagePart(MessagePartType type, String text) implements MessagePart {

    public MessagePartType type() {
        return MessagePartType.text;
    }

}
