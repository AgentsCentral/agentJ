package ai.agentscentral.mongodb.codec;

import ai.agentscentral.core.session.message.AssistantMessage;
import ai.agentscentral.core.session.message.DeveloperMessage;
import ai.agentscentral.core.session.message.HandOffMessage;
import ai.agentscentral.core.session.message.Message;
import ai.agentscentral.core.session.message.MessagePart;
import ai.agentscentral.core.session.message.TextPart;
import ai.agentscentral.core.session.message.ToolMessage;
import ai.agentscentral.core.session.message.UserMessage;
import ai.agentscentral.mongodb.model.MessageDocument;

import java.util.Arrays;
import java.util.List;

import static ai.agentscentral.core.session.message.MessageType.*;

public class MessageConverter {

    public static Message toMessage(MessageDocument messageDocument) {
        return switch (messageDocument.getType()) {
            case USER_MESSAGE -> toUserMessage(messageDocument);
            case ASSISTANT_MESSAGE -> toAssistantMessage(messageDocument);
            case TOOL_MESSAGE -> toToolMessage(messageDocument);
            case HANDOFF_MESSAGE -> toHandoffMessage(messageDocument);
            case DEVELOPER_MESSAGE -> toDeveloperMessage(messageDocument);
        };
    }

    private static Message toUserMessage(MessageDocument messageDocument) {
        return new UserMessage(
                messageDocument.getContextId(),
                messageDocument.getMessageId(),
                messageDocument.getType(),
                messageDocument.getTextParts().toArray(new MessagePart[0]),
                messageDocument.getTimestamp()
        );
    }

    private static Message toAssistantMessage(MessageDocument messageDocument) {
        return new AssistantMessage(
                messageDocument.getContextId(),
                messageDocument.getMessageId(),
                messageDocument.getType(),
                messageDocument.getTextParts().toArray(new MessagePart[0]),
                messageDocument.getToolCalls(),
                messageDocument.getHandoffs(),
                messageDocument.getTimestamp()
        );
    }

    private static Message toToolMessage(MessageDocument messageDocument) {
        return new ToolMessage(
                messageDocument.getContextId(),
                messageDocument.getMessageId(),
                messageDocument.getType(),
                messageDocument.getToolCallId(),
                messageDocument.getToolName(),
                messageDocument.getTextParts().toArray(new MessagePart[0]),
                messageDocument.getTimestamp()
        );
    }

    private static Message toHandoffMessage(MessageDocument messageDocument) {
        return new HandOffMessage(
                messageDocument.getContextId(),
                messageDocument.getMessageId(),
                messageDocument.getType(),
                messageDocument.getHandOffId(),
                messageDocument.getAgentName(),
                messageDocument.getTextParts().toArray(new MessagePart[0]),
                messageDocument.getTimestamp()
        );
    }

    private static Message toDeveloperMessage(MessageDocument messageDocument) {
        return new DeveloperMessage(
                messageDocument.getContextId(),
                messageDocument.getMessageId(),
                messageDocument.getType(),
                messageDocument.getTextParts().toArray(new MessagePart[0]),
                messageDocument.getTimestamp()
        );
    }

    public static MessageDocument toMessageDocument(Message message) {
        return switch (message.type()) {
            case USER_MESSAGE -> toUserMessageDocument((UserMessage) message);
            case ASSISTANT_MESSAGE -> toAssistantMessageDocument((AssistantMessage) message);
            case TOOL_MESSAGE -> toToolMessageDocument((ToolMessage) message);
            case HANDOFF_MESSAGE -> toHandoffMessageDocument((HandOffMessage) message);
            case DEVELOPER_MESSAGE -> toDeveloperMessageDocument((DeveloperMessage) message);
        };
    }

    private static MessageDocument toUserMessageDocument(UserMessage userMessage) {
        List<TextPart> textParts = Arrays.stream(userMessage.parts())
                .filter(TextPart.class::isInstance)
                .map(TextPart.class::cast)
                .toList();
                
        return new MessageDocument(
                userMessage.contextId(),
                userMessage.messageId(),
                userMessage.type(),
                textParts,
                userMessage.timestamp(),
                null, null, null, null, null, null
        );
    }

    private static MessageDocument toAssistantMessageDocument(AssistantMessage assistantMessage) {
        List<TextPart> textParts = Arrays.stream(assistantMessage.parts())
                .filter(TextPart.class::isInstance)
                .map(TextPart.class::cast)
                .toList();

        return new MessageDocument(
                assistantMessage.contextId(),
                assistantMessage.messageId(),
                assistantMessage.type(),
                textParts,
                assistantMessage.timestamp(),
                assistantMessage.toolCalls(), assistantMessage.handoffs(), null, null, null, null
        );
    }

    private static MessageDocument toHandoffMessageDocument(HandOffMessage handOffMessage) {
        List<TextPart> textParts = Arrays.stream(handOffMessage.parts())
                .filter(TextPart.class::isInstance)
                .map(TextPart.class::cast)
                .toList();

        return new MessageDocument(
                handOffMessage.contextId(),
                handOffMessage.messageId(),
                handOffMessage.type(),
                textParts,
                handOffMessage.timestamp(),
                null, null, handOffMessage.handOffId(), handOffMessage.agentName(), null, null
        );
    }

    private static MessageDocument toToolMessageDocument(ToolMessage toolMessage) {
        List<TextPart> textParts = Arrays.stream(toolMessage.parts())
                .filter(TextPart.class::isInstance)
                .map(TextPart.class::cast)
                .toList();

        return new MessageDocument(
                toolMessage.contextId(),
                toolMessage.messageId(),
                toolMessage.type(),
                textParts,
                toolMessage.timestamp(),
                null, null, null, null, toolMessage.toolCallId(), toolMessage.toolName()
        );
    }

    private static MessageDocument toDeveloperMessageDocument(DeveloperMessage developerMessage) {
        List<TextPart> textParts = Arrays.stream(developerMessage.parts())
                .filter(TextPart.class::isInstance)
                .map(TextPart.class::cast)
                .toList();

        return new MessageDocument(
                developerMessage.contextId(),
                developerMessage.messageId(),
                developerMessage.type(),
                textParts,
                developerMessage.timestamp(),
                null, null, null, null, null, null
        );
    }
}
