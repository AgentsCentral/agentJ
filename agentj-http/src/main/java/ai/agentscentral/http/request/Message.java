package ai.agentscentral.http.request;

import ai.agentscentral.http.common.MessageType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Polymorphic base interface for a single message part within an inbound HTTP
 * {@link MessageRequest}.
 *
 * <p>Jackson uses the {@code type} field to deserialise each element of the request's
 * {@code messages} array into one of:
 * <ul>
 *   <li>{@link TextMessage} ({@code "text"}) — a plain text turn from the user</li>
 *   <li>{@link InterruptMessage} ({@code "interrupt"}) — the user's response to a
 *       tool-call interrupt, carrying parameter values</li>
 * </ul>
 *
 * @author Rizwan Idrees
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TextMessage.class, name = "text"),
        @JsonSubTypes.Type(value = InterruptMessage.class, name = "interrupt"),
})
public interface Message {

    /**
     * Returns the message type discriminator used for polymorphic deserialisation.
     *
     * @return the {@link MessageType} of this message
     */
    MessageType type();
}
