package ai.agentscentral.http.request;

import ai.agentscentral.http.common.MessageType;

import java.util.List;

/**
 * InterruptMessage
 *
 * @param type
 * @param name
 * @param toolCallId
 * @param interruptParameters
 * @author Rizwan Idrees
 */
public record InterruptMessage(MessageType type,
                               String name,
                               String toolCallId,
                               List<InterruptParameter> interruptParameters) implements Message {

    public MessageType type() {
        return MessageType.interrupt;
    }
}
