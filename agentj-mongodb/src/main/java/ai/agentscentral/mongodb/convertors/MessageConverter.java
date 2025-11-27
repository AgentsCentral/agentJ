package ai.agentscentral.mongodb.convertors;

import ai.agentscentral.core.convertors.Convertor;
import ai.agentscentral.core.handoff.HandoffInstruction;
import ai.agentscentral.core.session.message.AssistantMessage;
import ai.agentscentral.core.session.message.DeveloperMessage;
import ai.agentscentral.core.session.message.HandOffMessage;
import ai.agentscentral.core.session.message.Message;
import ai.agentscentral.core.session.message.MessagePart;
import ai.agentscentral.core.session.message.ToolInterruptMessage;
import ai.agentscentral.core.session.message.ToolMessage;
import ai.agentscentral.core.session.message.UserInterruptMessage;
import ai.agentscentral.core.session.message.UserMessage;
import ai.agentscentral.core.tool.ToolCallInstruction;
import ai.agentscentral.mongodb.enums.MessageType;
import ai.agentscentral.mongodb.model.HandoffInstructionDocument;
import ai.agentscentral.mongodb.model.MessageDocument;
import ai.agentscentral.mongodb.model.MessagePartDocument;
import ai.agentscentral.mongodb.model.ToolCallInstructionDocument;

import java.util.Arrays;
import java.util.List;

/**
 * MessageConverter
 *
 * @author Mustafa Bhuiyan
 */
public class MessageConverter {

    private static List<MessagePartDocument> toMessagePartDocs(MessagePart[] parts) {
        return Arrays.stream(parts).map(MessagePartDocument::toMessagePartDoc).toList();
    }

    private static List<ToolCallInstructionDocument> toToolCallInstructionDocs(List<ToolCallInstruction> toolCallInstructions) {
        return toolCallInstructions.stream().map(ToolCallInstructionDocument::toToolCallInstructionDoc).toList();
    }

    private static List<HandoffInstructionDocument> toHandoffInstructionDocs(List<HandoffInstruction> handoffInstructions) {
        return handoffInstructions.stream().map(HandoffInstructionDocument::toHandoffInstructionDoc).toList();
    }

    // Converter to convert Message -> MessageDocument
    public static final Convertor<Message, MessageDocument> toMessageDocumentConverter = (message) -> {
        MessageDocument doc = new MessageDocument();
        doc.setMessageType(determineMessageType(message));

        doc.setContextId(message.contextId());
        doc.setMessageId(message.messageId());
        doc.setMessagePartsDocs(toMessagePartDocs(message.parts()));
        doc.setTimestamp(message.timestamp());

        // Handle specific message types
        switch (message) {
            case AssistantMessage am -> {
                // AssistantMessage fields
                doc.setToolCallDocs(toToolCallInstructionDocs(am.toolCalls()));
                doc.setHandoffDocs(toHandoffInstructionDocs(am.handoffs()));
            }
            case DeveloperMessage dm -> {
                // DeveloperMessage fields
            }
            case HandOffMessage hm -> {
                // HandOffMessage fields
                doc.setHandOffId(hm.handOffId());
                doc.setAgentName(hm.agentName());
            }
            case ToolInterruptMessage tim -> {
                // ToolInterruptMessage fields
            }
            case ToolMessage tm -> {
                // ToolMessage fields
                doc.setToolCallId(tm.toolCallId());
                doc.setToolName(tm.toolName());
            }
            case UserInterruptMessage uim -> {
                // UserInterruptMessage fields
                doc.setInterruptParameterValues(uim.interruptParameterValues());
            }
            case UserMessage um -> {
                // UserMessage fields
            }
            default -> {
                // No additional fields needed for other message types
            }
        }

        return doc;
    };

    private static MessagePart[] toMessageParts(List<MessagePartDocument> messagePartDocs) {
        return messagePartDocs.stream().map(MessagePartDocument::toMessagePart).toArray(MessagePart[]::new);
    }

    private static List<ToolCallInstruction> toToolCallInstructions(List<ToolCallInstructionDocument> toolCallInstructionDocs) {
        return toolCallInstructionDocs.stream().map(ToolCallInstructionDocument::toToolCallInstruction).toList();
    }

    private static List<HandoffInstruction> toHandoffInstructions(List<HandoffInstructionDocument> handoffInstructionDocs) {
        return handoffInstructionDocs.stream().map(HandoffInstructionDocument::toHandoffInstruction).toList();
    }

    // Converter to convert MessageDocument -> Message
    public static final Convertor<MessageDocument, Message> toMessageConverter = (doc) -> {
        return switch (doc.getMessageType()) {
            case ASSISTANT_MESSAGE -> new AssistantMessage(
                    doc.getContextId(),
                    doc.getMessageId(),
                    toMessageParts(doc.getMessagePartsDocs()),
                    toToolCallInstructions(doc.getToolCallDocs()),
                    toHandoffInstructions(doc.getHandoffDocs()),
                    doc.getTimestamp()
            );
            case DEVELOPER_MESSAGE -> new DeveloperMessage(
                    doc.getContextId(),
                    doc.getMessageId(),
                    toMessageParts(doc.getMessagePartsDocs()),
                    doc.getTimestamp()
            );
            case HANDOFF_MESSAGE -> new HandOffMessage(
                    doc.getContextId(),
                    doc.getMessageId(),
                    doc.getHandOffId(),
                    doc.getAgentName(),
                    toMessageParts(doc.getMessagePartsDocs()),
                    doc.getTimestamp()
            );
            case TOOL_MESSAGE -> new ToolMessage(
                    doc.getContextId(),
                    doc.getMessageId(),
                    doc.getToolCallId(),
                    doc.getToolName(),
                    toMessageParts(doc.getMessagePartsDocs()),
                    doc.getTimestamp()
            );
            case USER_MESSAGE -> new UserMessage(
                    doc.getContextId(),
                    doc.getMessageId(),
                    toMessageParts(doc.getMessagePartsDocs()),
                    doc.getTimestamp()
            );
            case USER_INTERRUPT_MESSAGE -> new UserInterruptMessage(
                    doc.getContextId(),
                    doc.getMessageId(),
                    doc.getInterruptParameterValues(),
                    toMessageParts(doc.getMessagePartsDocs()),
                    doc.getTimestamp()
            );
            case TOOL_INTERRUPT_MESSAGE -> new ToolInterruptMessage(
                    doc.getContextId(),
                    doc.getMessageId(),
                    toMessageParts(doc.getMessagePartsDocs()),
                    doc.getTimestamp()
            );
            default -> throw new IllegalStateException("Unsupported message type: " + doc.getMessageType());
        };
    };

    private static MessageType determineMessageType(Message message) {
        return switch (message) {
            case AssistantMessage am -> MessageType.ASSISTANT_MESSAGE;
            case DeveloperMessage dm -> MessageType.DEVELOPER_MESSAGE;
            case HandOffMessage hm -> MessageType.HANDOFF_MESSAGE;
            case ToolInterruptMessage tim -> MessageType.TOOL_INTERRUPT_MESSAGE;
            case ToolMessage tm -> MessageType.TOOL_MESSAGE;
            case UserInterruptMessage uim -> MessageType.USER_INTERRUPT_MESSAGE;
            case UserMessage um -> MessageType.USER_MESSAGE;
            default -> throw new IllegalArgumentException("Unsupported message type: " + message.getClass().getName());
        };
    }
}
