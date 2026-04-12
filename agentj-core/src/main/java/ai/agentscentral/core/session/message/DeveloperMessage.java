package ai.agentscentral.core.session.message;

/**
 * Represents an instruction or annotation injected into the conversation by the
 * application developer rather than by the end-user or the model.
 *
 * <p>Developer messages are included in the context history sent to the provider,
 * allowing the application to supply additional guidance mid-conversation (e.g. runtime
 * context updates, policy reminders, or debug annotations) without those appearing as
 * user-facing content.</p>
 *
 * @param contextId the conversation context identifier
 * @param messageId unique identifier for this message
 * @param parts     content parts carrying the developer-supplied text
 * @param timestamp epoch-millisecond creation timestamp
 *
 * @author Rizwan Idrees
 */
public record DeveloperMessage(String contextId, String messageId, MessagePart[] parts, long timestamp) implements Message {
}
