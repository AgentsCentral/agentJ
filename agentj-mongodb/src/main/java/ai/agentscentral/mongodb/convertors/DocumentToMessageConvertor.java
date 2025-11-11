package ai.agentscentral.mongodb.convertors;

import ai.agentscentral.core.convertors.Convertor;
import ai.agentscentral.core.session.message.*;
import org.bson.Document;

/**
 * MessageConvertor
 *
 * @author Mustafa Kamal
 * @author Rizwan Idrees
 */
public class DocumentToMessageConvertor {

    private DocumentToMessageConvertor() {
    }

    public static final Convertor<Document, Message> documentToMessageConvertor = d ->
            switch (d.getString("class")) {
                case  "ai.agentscentral.core.session.message.AssistantMessage.class" ->  documentToAssistantMessage(d);
//                case DeveloperMessage dm -> developerMessageToDocument(dm);
//                case HandOffMessage hm -> handoffMessageToDocument(hm);
//                case ToolInterruptMessage tim -> toolInterruptMessageToDocument(tim);
//                case ToolMessage tm -> toolMessageToDocument(tm);
//                case UserInterruptMessage uim -> userInterruptMessageToDocument(uim);
//                case UserMessage um -> userMessageToDocument(um);
                default -> throw new UnsupportedOperationException("");
            };


    private static AssistantMessage documentToAssistantMessage(Document document) {
        return new AssistantMessage(
                document.getString("contextId"),
                document.getString("messageId"),
                null,
                null,
                null,
                document.getLong("timestamp")
        );

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
        return new Document("_class", message.getClass())
                .append("messageId", message.messageId())
                .append("contextId", message.contextId())
                .append("parts", message.parts())
                .append("timestamp", message.timestamp());
    }
}
