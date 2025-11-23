package ai.agentscentral.mongodb.convertors;

import ai.agentscentral.core.convertors.Convertor;
import ai.agentscentral.core.session.message.AssistantMessage;
import ai.agentscentral.core.session.message.DeveloperMessage;
import ai.agentscentral.core.session.message.HandOffMessage;
import ai.agentscentral.core.session.message.Message;
import ai.agentscentral.core.session.message.ToolInterruptMessage;
import ai.agentscentral.core.session.message.ToolMessage;
import ai.agentscentral.core.session.message.UserInterruptMessage;
import ai.agentscentral.core.session.message.UserMessage;
import org.bson.Document;

/**
 * MessageConvertor
 *
 * @author Mustafa Kamal
 * @author Rizwan Idrees
 */
public class MessageToDocumentConvertor {

    private MessageToDocumentConvertor() {
    }

    public static final Convertor<Message, Document> messageToDocumentConvertor = m ->
            switch (m) {
                case AssistantMessage am -> assistantMessageToDocument(am);
                case DeveloperMessage dm -> developerMessageToDocument(dm);
                case HandOffMessage hm -> handoffMessageToDocument(hm);
                case ToolInterruptMessage tim -> toolInterruptMessageToDocument(tim);
                case ToolMessage tm -> toolMessageToDocument(tm);
                case UserInterruptMessage uim -> userInterruptMessageToDocument(uim);
                case UserMessage um -> userMessageToDocument(um);
                default -> throw new UnsupportedOperationException("Failed to convert message to document");
            };


    private static Document assistantMessageToDocument(AssistantMessage message) {
        return defaultMessage(message)
                .append("toolCalls", message.toolCalls())
                .append("handoffs", message.handoffs());

    }

    private static Document developerMessageToDocument(DeveloperMessage message) {
        return defaultMessage(message);
    }

    private static Document handoffMessageToDocument(HandOffMessage message) {
        return defaultMessage(message)
                .append("handOffId", message.handOffId())
                .append("agentName", message.agentName());
    }

    private static Document toolInterruptMessageToDocument(ToolInterruptMessage message) {
        return defaultMessage(message);
    }

    private static Document toolMessageToDocument(ToolMessage message) {
        return defaultMessage(message)
                .append("toolCallId", message.toolCallId())
                .append("toolName", message.toolName());
    }

    private static Document userInterruptMessageToDocument(UserInterruptMessage message) {
        return defaultMessage(message)
                .append("interruptParameterValues", message.interruptParameterValues());
    }

    private static Document userMessageToDocument(UserMessage message) {
        return defaultMessage(message);
    }

    private static Document defaultMessage(Message message) {
        return new Document("_class", message.getClass().getName())
                .append("messageId", message.messageId())
                .append("contextId", message.contextId())
                .append("parts", message.parts())
                .append("timestamp", message.timestamp());
    }
}
