package ai.agentscentral.core.session.context;

import ai.agentscentral.core.session.message.Message;

import java.util.List;

/**
 * ConversationContextManager
 *
 * @author Rizwan Idrees
 */
public interface SessionContextManager {


    List<Message> getContext(String contextId);
    List<Message> addContext(String contextId, List<? extends Message> newMessages);

}
