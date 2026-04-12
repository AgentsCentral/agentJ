package ai.agentscentral.http.response;

import ai.agentscentral.http.common.MessageType;

/**
 * Outbound response message signalling that the agent has paused and requires user input
 * to resolve one or more tool-call interrupts.
 *
 * <p>The {@code type} component always returns {@link MessageType#interrupt} via the
 * {@link #type()} override.  Each element of {@code interrupt} describes a single tool
 * call that needs parameter values supplied by the user before execution can continue.</p>
 *
 * @param type      the message type discriminator; always {@link MessageType#interrupt}
 * @param id        unique identifier for this message
 * @param interrupt array of {@link InterruptPart} descriptors, one per pending tool call
 * @param timestamp creation time of this message in epoch milliseconds
 *
 * @author Rizwan Idrees
 */
public record InterruptMessage(MessageType type,
                               String id,
                               InterruptPart[] interrupt,
                               long timestamp) implements Message {

    public MessageType type() {
        return MessageType.interrupt;
    }
}
