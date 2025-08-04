package ai.agentscentral.http.servlet;

import ai.agentscentral.core.session.id.MessageIdGenerator;
import ai.agentscentral.core.session.id.SessionIdGenerator;
import ai.agentscentral.core.session.message.*;
import ai.agentscentral.core.session.processor.SessionProcessor;
import ai.agentscentral.http.request.MessageRequest;
import ai.agentscentral.http.request.RequestExtractor;
import ai.agentscentral.http.request.SessionIdExtractor;
import ai.agentscentral.http.response.MessageResponse;
import ai.agentscentral.http.response.MessageType;
import ai.agentscentral.http.response.ResponseSender;
import ai.agentscentral.http.response.TextMessage;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ai.agentscentral.core.session.message.MessagePartType.text;
import static java.lang.System.currentTimeMillis;


/**
 * AgentJServlet
 *
 * @author Rizwan Idrees
 */
public class AgentJServlet extends HttpServlet {

    private final SessionProcessor processor;
    private final RequestExtractor requestExtractor;
    private final ResponseSender responseSender;
    private final SessionIdExtractor sessionIdExtractor;
    private final SessionIdGenerator sessionIdGenerator;
    private final MessageIdGenerator messageIdGenerator;

    public AgentJServlet(SessionProcessor processor,
                         RequestExtractor requestExtractor,
                         ResponseSender responseSender,
                         SessionIdExtractor sessionIdExtractor,
                         SessionIdGenerator sessionIdGenerator,
                         MessageIdGenerator messageIdGenerator) {
        this.processor = processor;
        this.requestExtractor = requestExtractor;
        this.responseSender = responseSender;
        this.sessionIdExtractor = sessionIdExtractor;
        this.sessionIdGenerator = sessionIdGenerator;
        this.messageIdGenerator = messageIdGenerator;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        final MessageRequest messageRequest = requestExtractor.extract(request);
        final String sessionId = sessionIdExtractor.extract(request)
                .orElse(sessionIdGenerator.generate());

        final List<AssistantMessage> assistantMessages = Optional.of(messageRequest)
                .map(r -> new UserMessage(sessionId, messageId(), toTextParts(r), currentTimeMillis()))
                .map(um -> processor.process(sessionId, um, null)).orElseGet(() -> List.of());

        final List<TextMessage> textMessages = assistantMessages.stream()
                .map(am -> toTextMessage(am.messageId(), am.parts(), am.timestamp()))
                .toList();

        responseSender.send(response, new MessageResponse(sessionId, textMessages));
    }

    private String messageId() {
        return messageIdGenerator.generate();
    }

    private TextMessage toTextMessage(String id, MessagePart[] parts, long timestamp) {
        final String text = Stream.of(parts)
                .filter(messagePart -> messagePart.type() == MessagePartType.text)
                .map(t -> (TextPart) t)
                .map(TextPart::text).collect(Collectors.joining());

        return new TextMessage(MessageType.text, id, text, timestamp);
    }

    private static TextPart[] toTextParts(MessageRequest messageRequest) {
        return new TextPart[]{new TextPart(text, messageRequest.message())};
    }
}
