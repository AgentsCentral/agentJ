package ai.agentscentral.http.handler;

import ai.agentscentral.core.session.id.MessageIdGenerator;
import ai.agentscentral.core.session.id.SessionIdGenerator;
import ai.agentscentral.core.session.message.*;
import ai.agentscentral.core.session.message.Message;
import ai.agentscentral.core.session.processor.SessionProcessor;
import ai.agentscentral.http.common.MessageType;
import ai.agentscentral.http.config.AgenticConfig;
import ai.agentscentral.http.request.MessageRequest;
import ai.agentscentral.http.request.Request;
import ai.agentscentral.http.request.RequestExtractor;
import ai.agentscentral.http.request.SessionIdExtractor;
import ai.agentscentral.http.response.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static ai.agentscentral.core.session.message.MessagePartType.text;
import static ai.agentscentral.core.session.message.MessagePartType.user_interrupt;
import static ai.agentscentral.http.common.MessageType.interrupt;
import static java.lang.System.currentTimeMillis;

/**
 * {@link HttpHandler} that bridges an HTTP request to an AgentJ agentic session.
 *
 * <p>On each {@link #handle} call the handler:
 * <ol>
 *   <li>Deserialises the request body into a {@link ai.agentscentral.http.request.MessageRequest}
 *       via the configured {@link ai.agentscentral.http.request.RequestExtractor}.</li>
 *   <li>Resolves or generates a session identifier using the
 *       {@link ai.agentscentral.http.request.SessionIdExtractor} and
 *       {@link ai.agentscentral.core.session.id.SessionIdGenerator}.</li>
 *   <li>Converts each request message part into a
 *       {@link ai.agentscentral.core.session.message.MessagePart} (text or interrupt)
 *       and wraps them in a {@link ai.agentscentral.core.session.message.UserMessage}.</li>
 *   <li>Passes the user message to the {@link ai.agentscentral.core.session.processor.SessionProcessor}.</li>
 *   <li>Maps the resulting {@link ai.agentscentral.core.session.message.Message}s back to
 *       HTTP response message types ({@link ai.agentscentral.http.response.TextMessage}
 *       or {@link ai.agentscentral.http.response.InterruptMessage}) and returns a
 *       {@code 200 application/json} response.</li>
 * </ol>
 *
 * @author Rizwan Idrees
 */
public class AgenticHttpHandler implements HttpHandler<MessageResponse> {

    private final SessionProcessor processor;
    private final RequestExtractor requestExtractor;
    private final SessionIdExtractor sessionIdExtractor;
    private final SessionIdGenerator sessionIdGenerator;
    private final MessageIdGenerator messageIdGenerator;

    /**
     * Creates an {@code AgenticHttpHandler} from a fully assembled {@link AgenticConfig}.
     *
     * @param config the agentic configuration supplying all required dependencies
     */
    public AgenticHttpHandler(AgenticConfig config) {
        this(config.processor(),
                config.requestExtractor(),
                config.sessionIdExtractor(),
                config.sessionIdGenerator(),
                config.messageIdGenerator()
        );
    }

    /**
     * Creates an {@code AgenticHttpHandler} from individual dependencies.
     *
     * @param processor           the session processor that drives agent execution
     * @param requestExtractor    deserialises the HTTP request body
     * @param sessionIdExtractor  extracts an existing session ID from the request
     * @param sessionIdGenerator  generates a new session ID when none is present
     * @param messageIdGenerator  generates unique IDs for each user message
     */
    public AgenticHttpHandler(SessionProcessor processor,
                              RequestExtractor requestExtractor,
                              SessionIdExtractor sessionIdExtractor,
                              SessionIdGenerator sessionIdGenerator,
                              MessageIdGenerator messageIdGenerator) {

        this.processor = processor;
        this.requestExtractor = requestExtractor;
        this.sessionIdExtractor = sessionIdExtractor;
        this.sessionIdGenerator = sessionIdGenerator;
        this.messageIdGenerator = messageIdGenerator;
    }

    @Override
    public Response<MessageResponse> handle(Request request) {
        final MessageRequest messageRequest = requestExtractor.extract(request);
        final String sessionId = sessionIdExtractor.extract(request)
                .orElse(sessionIdGenerator.generate());

        final List<Message> messages = Optional.of(messageRequest)
                .map(r -> new UserMessage(sessionId, messageId(), toMessageParts(r), currentTimeMillis()))
                .map(um -> processor.process(sessionId, um, null)).orElseGet(List::of);

        final List<ai.agentscentral.http.response.Message> responseMessages = messages.stream()
                .map(this::toMessage)
                .toList();

        return Response.<MessageResponse>response(200, "application/json")
                .resource(new MessageResponse(sessionId, responseMessages))
                .build();
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

    private MessagePart[] toMessageParts(MessageRequest messageRequest) {
        return messageRequest.messages().stream()
                .map(this::toMessagePart).toArray(MessagePart[]::new);

    }

    private MessagePart toMessagePart(ai.agentscentral.http.request.Message message) {
        return switch (message) {
            case ai.agentscentral.http.request.TextMessage m -> toTextPart(m);
            case ai.agentscentral.http.request.InterruptMessage m -> toUserInterruptPart(m);
            default -> throw new RuntimeException("Unknown message part type");
        };
    }

    private static TextPart toTextPart(ai.agentscentral.http.request.TextMessage message) {
        return new TextPart(text, message.text());
    }

    private static UserInterruptPart toUserInterruptPart(ai.agentscentral.http.request.InterruptMessage message) {
        final List<InterruptParameterValue> parameterValues = message.interruptParameters().stream()
                .map(p -> new InterruptParameterValue(p.name(), p.value())).toList();
        return new UserInterruptPart(user_interrupt, message.toolCallId(), message.name(), parameterValues);
    }

    private String messageId() {
        return messageIdGenerator.generate();
    }

}
