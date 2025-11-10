package ai.agentscentral.mongodb.model;

import ai.agentscentral.core.handoff.HandoffInstruction;
import ai.agentscentral.core.session.message.MessagePart;
import ai.agentscentral.core.session.message.MessageType;
import ai.agentscentral.core.session.message.TextPart;
import ai.agentscentral.core.tool.ToolCallInstruction;

import java.util.List;

public record MessageDocument(
        // Common fields for all message types
        String contextId,
        String messageId,
        MessageType type,
        List<TextPart> textParts,
        Long timestamp,

        // AssistanceMessage specific fields
        List<ToolCallInstruction> toolCalls,
        List<HandoffInstruction> handoffs,

        // HandOffMessage specific fields
        String handOffId,
        String agentName,

        // ToolMessage specific fields
        String toolCallId,
        String toolName
) {
}
