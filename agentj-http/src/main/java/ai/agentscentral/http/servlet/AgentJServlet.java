package ai.agentscentral.http.servlet;

import ai.agentscentral.core.conversation.ConversationIdGenerator;
import ai.agentscentral.core.conversation.ConversationProcessor;
import ai.agentscentral.core.conversation.MessageIdGenerator;
import ai.agentscentral.core.conversation.message.AssistantMessage;
import ai.agentscentral.core.conversation.message.TextPart;
import ai.agentscentral.core.conversation.message.UserMessage;
import ai.agentscentral.http.request.ConversationIdExtractor;
import ai.agentscentral.http.request.MessageRequest;
import ai.agentscentral.http.request.RequestExtractor;
import ai.agentscentral.http.response.MessageResponse;
import ai.agentscentral.http.response.ResponseSender;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static ai.agentscentral.core.conversation.message.MessagePartType.text;
import static java.lang.System.currentTimeMillis;


/**
 * AgentJServlet
 *
 * @author Rizwan Idrees
 */
public class AgentJServlet extends HttpServlet {

    private final ConversationProcessor processor;
    private final RequestExtractor requestExtractor;
    private final ResponseSender responseSender;
    private final ConversationIdExtractor conversationIdExtractor;
    private final ConversationIdGenerator conversationIdGenerator;
    private final MessageIdGenerator messageIdGenerator;

    public AgentJServlet(ConversationProcessor processor,
                         RequestExtractor requestExtractor,
                         ResponseSender responseSender,
                         ConversationIdExtractor conversationIdExtractor,
                         ConversationIdGenerator conversationIdGenerator,
                         MessageIdGenerator messageIdGenerator) {
        this.processor = processor;
        this.requestExtractor = requestExtractor;
        this.responseSender = responseSender;
        this.conversationIdExtractor = conversationIdExtractor;
        this.conversationIdGenerator = conversationIdGenerator;
        this.messageIdGenerator = messageIdGenerator;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final MessageRequest messageRequest = requestExtractor.extract(request);
        final String conversationId = conversationIdExtractor.extract(request)
                .orElse(conversationIdGenerator.generate());

        final List<AssistantMessage> assistantMessages = Optional.of(messageRequest)
                .map(r -> new UserMessage(conversationId, messageId(), toTextParts(r), currentTimeMillis()))
                .map(um -> processor.process(conversationId, um)).orElse(List.of());

        final List<String> messages = assistantMessages.stream()
                .flatMap(am -> Stream.of(am.parts()))
                .filter(messagePart -> messagePart.type() == text)
                .map(t -> (TextPart) t)
                .map(TextPart::text)
                .toList();

        responseSender.send(response, new MessageResponse(conversationId, messages));

    }

    private String messageId() {
        return messageIdGenerator.generate();
    }

    private static TextPart[] toTextParts(MessageRequest messageRequest) {
        return new TextPart[]{new TextPart(text, messageRequest.message())};
    }
}
