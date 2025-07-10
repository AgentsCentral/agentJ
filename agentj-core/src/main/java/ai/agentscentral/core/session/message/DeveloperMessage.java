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
public record DeveloperMessage(String contextId, String messageId, MessagePart[] parts, long timestamp) implements Message {
}
