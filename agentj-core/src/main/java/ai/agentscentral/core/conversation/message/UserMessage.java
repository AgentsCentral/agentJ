package ai.agentscentral.core.conversation.message;

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
public record UserMessage(String contextId, String messageId, MessagePart[] parts, long timestamp) implements Message {
}
