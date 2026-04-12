package ai.agentscentral.http.response;

import ai.agentscentral.http.common.MessageType;

/**
 * Outbound response message carrying one or more text segments from the agent.
 *
 * <p>The {@code type} component always returns {@link MessageType#text} via the
 * {@link #type()} override.</p>
 *
 * @param type      the message type discriminator; always {@link MessageType#text}
 * @param id        unique identifier for this message
 * @param text      ordered array of text segments produced by the agent
 * @param timestamp creation time of this message in epoch milliseconds
 *
 * @author Rizwan Idrees
 */
public record TextMessage(MessageType type, String id, String[] text, long timestamp) implements Message {

    public MessageType type() {
        return MessageType.text;
    }
}
