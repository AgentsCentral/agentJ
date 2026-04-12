package ai.agentscentral.http.request;

import ai.agentscentral.http.common.MessageType;

/**
 * Inbound {@link Message} carrying a plain text turn from the user.
 *
 * <p>The {@link #type()} override always returns {@link MessageType#text}.</p>
 *
 * @param type the type discriminator; always overridden to {@link MessageType#text}
 * @param text the user's text input
 *
 * @author Rizwan Idrees
 */
public record TextMessage(MessageType type, String text) implements Message {

    public MessageType type() {
        return MessageType.text;
    }
}
