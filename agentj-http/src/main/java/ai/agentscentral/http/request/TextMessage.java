package ai.agentscentral.http.request;

import ai.agentscentral.http.common.MessageType;

/**
 * TextMessage
 *
 * @param type
 * @param text
 * @author Rizwan Idrees
 */
public record TextMessage(MessageType type, String text) implements Message {

    public MessageType type() {
        return MessageType.text;
    }
}
