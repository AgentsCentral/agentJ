package ai.agentscentral.core.conversation.message;

/**
 * ToolMessage
 * @param contextId
 * @param messageId
 * @param toolCallId
 * @param toolName
 * @param parts
 * @param timestamp
 *
 * @author Rizwan Idrees
 */
public record ToolMessage(String contextId,
                          String messageId,
                          String toolCallId,
                          String toolName,
                          MessagePart[] parts,
                          long timestamp) implements Message {
}
