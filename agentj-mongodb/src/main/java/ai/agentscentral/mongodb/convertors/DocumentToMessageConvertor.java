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
import org.bson.Document;

import java.util.Map;

/**
 * MessageConvertor
 *
 * @author Mustafa Bhuiyan
 * @author Rizwan Idrees
 */
public class DocumentToMessageConvertor {

    private DocumentToMessageConvertor() {
    }

    public static final Convertor<Document, Message> documentToMessageConvertor = d ->
            switch (d.getString("_class")) {
                case  "AssistantMessage.class" ->  documentToAssistantMessage(d);
                case "DeveloperMessage" -> documentToDeveloperMessage(d);
                case "HandOffMessage" -> documentToHandoffMessage(d);
                case "ToolInterruptMessage" -> documentToToolInterruptMessage(d);
                case "ToolMessage" -> documentToToolMessage(d);
                case "UserInterruptMessage" -> documentToUserInterruptMessage(d);
                case "UserMessage" -> documentToUserMessage(d);
                default -> throw new UnsupportedOperationException("Failed to convert document to message");
            };


    private static AssistantMessage documentToAssistantMessage(Document document) {
        return new AssistantMessage(
                document.getString("contextId"),
                document.getString("messageId"),
                document.getList("parts", MessagePart.class).toArray(new MessagePart[0]),
                document.getList("toolCalls", ToolCallInstruction.class),
                document.getList("handoffs", HandoffInstruction.class),
                document.getLong("timestamp")
        );

    }

    private static DeveloperMessage documentToDeveloperMessage(Document document) {
        return new DeveloperMessage(
                document.getString("contextId"),
                document.getString("messageId"),
                document.getList("parts", MessagePart.class).toArray(new MessagePart[0]),
                document.getLong("timestamp")
        );
    }

    private static HandOffMessage documentToHandoffMessage(Document document) {
        return new HandOffMessage(
                document.getString("contextId"),
                document.getString("messageId"),
                document.getString("handOffId"),
                document.getString("agentName"),
                document.getList("parts", MessagePart.class).toArray(new MessagePart[0]),
                document.getLong("timestamp")
        );
    }

    private static ToolInterruptMessage documentToToolInterruptMessage(Document document) {
        return new ToolInterruptMessage(
                document.getString("contextId"),
                document.getString("messageId"),
                document.getList("parts", MessagePart.class).toArray(new MessagePart[0]),
                document.getLong("timestamp")
        );
    }

    private static ToolMessage documentToToolMessage(Document document) {
        return new ToolMessage(
                document.getString("contextId"),
                document.getString("messageId"),
                document.getString("toolCallId"),
                document.getString("toolName"),
                document.getList("parts", MessagePart.class).toArray(new MessagePart[0]),
                document.getLong("timestamp")
        );
    }

    private static UserInterruptMessage documentToUserInterruptMessage(Document document) {
        @SuppressWarnings("unchecked")
        Map<String, String> interruptParameterValues =
                (Map<String, String>) document.get("interruptParameterValues");
        return new UserInterruptMessage(
                document.getString("contextId"),
                document.getString("messageId"),
                interruptParameterValues,
                document.getList("parts", MessagePart.class).toArray(new MessagePart[0]),
                document.getLong("timestamp"));
    }

    private static UserMessage documentToUserMessage(Document document) {
        return new UserMessage(
                document.getString("contextId"),
                document.getString("messageId"),
                document.getList("parts", MessagePart.class).toArray(new MessagePart[0]),
                document.getLong("timestamp"));
    }
}
