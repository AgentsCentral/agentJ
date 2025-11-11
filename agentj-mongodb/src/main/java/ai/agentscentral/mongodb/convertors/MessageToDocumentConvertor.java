package ai.agentscentral.mongodb.convertors;

import ai.agentscentral.core.convertors.Convertor;
import ai.agentscentral.core.handoff.DefaultHandoffInstruction;
import ai.agentscentral.core.handoff.Handoff;
import ai.agentscentral.core.session.message.*;
import ai.agentscentral.core.tool.*;
import com.mongodb.MongoClientSettings;
import org.bson.Document;

import java.util.List;
import java.util.Map;

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
                default -> throw new UnsupportedOperationException("");
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
        return new Document("_class", message.getClass())
                .append("messageId", message.messageId())
                .append("contextId", message.contextId())
                .append("parts", message.parts())
                .append("timestamp", message.timestamp());
    }


    static final class SomeToolBag implements ToolBag{

        public void someTool(){

        }
    }

    /*
    public static void main(String...args) throws NoSuchMethodException {

        SomeToolBag someToolBag = new SomeToolBag();

        ToolCall toolCall = new ToolCall(someToolBag, someToolBag.getClass().getMethod("someTool"),
                "some toolName", "some description",
                List.of(new TypedToolParameter(0, String.class, "param", "param", true, "param")),
                List.of(),
                List.of()
        );

        TextPart[] parts = {new TextPart(MessagePartType.text, "test")};
        final DefaultToolCallInstruction tci = new DefaultToolCallInstruction("t1", "some_tool",
                "1,2", Map.of("somep1", "somep2"), toolCall);



        DefaultHandoffInstruction hoi = new DefaultHandoffInstruction("c1", new Handoff("1", "some_Ag", "someagi"));


        AssistantMessage am = new AssistantMessage("c1", "m1",
                parts, List.of(tci), List.of(hoi), System.currentTimeMillis());

    }

     */
}
