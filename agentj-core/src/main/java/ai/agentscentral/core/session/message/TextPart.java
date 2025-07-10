package ai.agentscentral.core.session.message;

/**
 * TextPart
 * @param type
 * @param text
 *
 * @author Rizwan Idrees
 */
public record TextPart(MessagePartType type, String text) implements MessagePart {

    public MessagePartType type() {
        return MessagePartType.text;
    }
}
