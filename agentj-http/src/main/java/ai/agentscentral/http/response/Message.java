package ai.agentscentral.http.response;

import ai.agentscentral.http.common.MessageType;

/**
 * Message
 *
 * @author Rizwan Idrees
 */
public interface Message {

    MessageType type();

    String id();

    long timestamp();

}
