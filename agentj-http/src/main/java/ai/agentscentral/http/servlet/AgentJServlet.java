package ai.agentscentral.http.servlet;

import ai.agentscentral.core.session.id.MessageIdGenerator;
import ai.agentscentral.core.session.id.SessionIdGenerator;
import ai.agentscentral.core.session.message.Message;
import ai.agentscentral.core.session.message.*;
import ai.agentscentral.core.session.processor.SessionProcessor;
import ai.agentscentral.http.request.MessageRequest;
import ai.agentscentral.http.request.RequestExtractor;
import ai.agentscentral.http.request.SessionIdExtractor;
import ai.agentscentral.http.response.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static ai.agentscentral.core.session.message.MessagePartType.text;
import static ai.agentscentral.http.response.MessageType.interrupt;
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

        final List<Message> messages = Optional.of(messageRequest)
                .map(r -> new UserMessage(sessionId, messageId(), toTextParts(r), currentTimeMillis()))
                .map(um -> processor.process(sessionId, um, null)).orElseGet(List::of);

        final List<ai.agentscentral.http.response.Message> responseMessages = messages.stream()
                .map(this::toMessage)
                .toList();

        responseSender.send(response, new MessageResponse(sessionId, responseMessages));
    }

    private String messageId() {
        return messageIdGenerator.generate();
    }


    private ai.agentscentral.http.response.Message toMessage(Message message) {
        if (message instanceof AssistantMessage assistantMessage) {
            return toTextMessage(assistantMessage.messageId(), assistantMessage.parts(),
                    assistantMessage.timestamp());
        } else if (message instanceof ToolInterruptMessage interruptMessage) {
            return toInterruptMessage(interruptMessage);
        }
        return null;
    }

    private InterruptMessage toInterruptMessage(ToolInterruptMessage interruptMessage) {
        final InterruptPart[] interruptParts = Stream.of(interruptMessage.parts())
                .map(t -> (ToolInterruptPart) t)
                .map(i -> new InterruptPart(i.interruptName(), i.toolCallId(), i.renderer(),
                        i.toolCallParameters(), i.interruptParameters()))
                .toArray(InterruptPart[]::new);

        return new InterruptMessage(interrupt, interruptMessage.messageId(), interruptParts,
                interruptMessage.timestamp());
    }

    private TextMessage toTextMessage(String id, MessagePart[] parts, long timestamp) {
        final String[] text = Stream.of(parts)
                .filter(messagePart -> messagePart.type() == MessagePartType.text)
                .map(t -> (TextPart) t)
                .map(TextPart::text).toArray(String[]::new);

        return new TextMessage(MessageType.text, id, text, timestamp);
    }

    private static TextPart[] toTextParts(MessageRequest messageRequest) {
        return new TextPart[]{new TextPart(text, messageRequest.message())};
    }
}
