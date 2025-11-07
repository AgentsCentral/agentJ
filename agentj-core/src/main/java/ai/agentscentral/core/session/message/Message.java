package ai.agentscentral.core.session.message;

/**
 * Message
 *
 * @author Rizwan Idrees
 */
public interface Message {

    String contextId();

    String messageId();

    MessageType type();

    MessagePart[] parts();

    long timestamp();
}
