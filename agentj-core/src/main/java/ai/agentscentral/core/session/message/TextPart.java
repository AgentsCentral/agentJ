package ai.agentscentral.core.session.message;

/**
 * A {@link MessagePart} carrying plain text content.
 *
 * <p>Used in {@link AssistantMessage} for model-generated text responses and in
 * {@link HandOffMessage} to describe the reason for a handoff. Always reports
 * {@link MessagePartType#text} from {@link #type()}, regardless of the value passed
 * to the constructor.</p>
 *
 * @param type unused constructor parameter; {@link #type()} always returns
 *             {@link MessagePartType#text}
 * @param text the plain text content
 *
 * @author Rizwan Idrees
 */
public record TextPart(MessagePartType type, String text) implements MessagePart {

    /**
     * Always returns {@link MessagePartType#text}.
     *
     * @return {@link MessagePartType#text}
     */
    public MessagePartType type() {
        return MessagePartType.text;
    }
}
