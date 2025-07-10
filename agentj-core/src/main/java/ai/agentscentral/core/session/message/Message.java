package ai.agentscentral.core.session.message;

/**
 * Message
 *
 * @author Rizwan Idrees
 */
public interface Message {

    String contextId();

    String messageId();

    MessagePart[] parts();

    long timestamp();
}
