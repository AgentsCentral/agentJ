package ai.agentscentral.http.response;

import ai.agentscentral.http.common.MessageType;

/**
 * InterruptMessage
 *
 * @param type
 * @param id
 * @param interrupt
 * @param timestamp
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
