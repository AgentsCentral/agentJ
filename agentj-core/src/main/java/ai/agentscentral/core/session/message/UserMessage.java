package ai.agentscentral.core.session.message;

/**
 * Represents a message sent by the end-user, initiating or continuing a conversational turn.
 *
 * <p>A {@code UserMessage} is the entry point for every execution cycle in
 * {@link ai.agentscentral.core.session.processor.DefaultSessionProcessor}. Its
 * {@link MessagePart}s typically contain a {@link TextPart} with the user's input text,
 * but may also include {@link UserInterruptPart}s when the user is responding to a
 * previously issued tool interrupt.</p>
 *
 * @param contextId the conversation context identifier
 * @param messageId unique identifier for this message
 * @param parts     the content parts of this message
 * @param timestamp epoch-millisecond creation timestamp
 *
 * @author Rizwan Idrees
 */
public record UserMessage(String contextId, String messageId, MessagePart[] parts, long timestamp) implements Message {
}
