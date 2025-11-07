package ai.agentscentral.core.session.message;

/**
 * ToolMessage
 *
 * @param contextId
 * @param messageId
 * @param toolCallId
 * @param toolName
 * @param parts
 * @param timestamp
 * @author Rizwan Idrees
 */
public record ToolMessage(String contextId,
                          String messageId,
                          MessageType type,
                          String toolCallId,
                          String toolName,
                          MessagePart[] parts,
                          long timestamp) implements Message {

    public ToolMessage(String contextId,
                       String messageId,
                       String toolCallId,
                       String toolName,
                       MessagePart[] parts,
                       long timestamp) {
        this(contextId, messageId, MessageType.TOOL_MESSAGE, toolCallId, toolName, parts, timestamp);
    }

    @Override
    public MessageType type() {
        return MessageType.TOOL_MESSAGE;
    }
}
