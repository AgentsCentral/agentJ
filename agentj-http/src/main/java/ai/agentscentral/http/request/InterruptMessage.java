package ai.agentscentral.http.request;

import ai.agentscentral.http.common.MessageType;

import java.util.List;

/**
 * Inbound {@link Message} carrying the user's response to a tool-call interrupt.
 *
 * <p>When an agent pauses execution to request user confirmation (a
 * {@link ai.agentscentral.core.session.message.ToolInterruptMessage}), the client
 * resumes the session by sending an {@code InterruptMessage} referencing the
 * original {@code toolCallId} and supplying any required parameter values.
 * The {@link #type()} override always returns {@link MessageType#interrupt}.</p>
 *
 * @param type                the type discriminator; always overridden to
 *                            {@link MessageType#interrupt}
 * @param name                the interrupt name that was originally presented to the user
 * @param toolCallId          the ID of the tool-use block being resumed
 * @param interruptParameters the parameter values supplied by the user
 *
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
