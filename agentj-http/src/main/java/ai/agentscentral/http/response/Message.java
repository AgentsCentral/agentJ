package ai.agentscentral.http.response;

import ai.agentscentral.http.common.MessageType;

/**
 * Polymorphic base interface for a single message part within an outbound HTTP
 * {@link MessageResponse}.
 *
 * <p>Concrete types:
 * <ul>
 *   <li>{@link TextMessage} — a completed text turn from the agent</li>
 *   <li>{@link InterruptMessage} — a tool-call interrupt that requires user input</li>
 * </ul>
 *
 * @author Rizwan Idrees
 */
public interface Message {

    /**
     * Returns the message type discriminator.
     *
     * @return the {@link MessageType} of this message
     */
    MessageType type();

    /**
     * Returns the unique identifier for this message.
     *
     * @return the message ID
     */
    String id();

    /**
     * Returns the creation timestamp of this message in epoch milliseconds.
     *
     * @return epoch-millisecond timestamp
     */
    long timestamp();

}
