package ai.agentscentral.core.context;

import ai.agentscentral.core.session.message.Message;

import java.util.List;

/**
 * ContextManager
 *
 * @author Rizwan Idrees
 */
public interface ContextManager {

    List<Message> getContext(String contextId);
    void addContext(String contextId, List<? extends Message> newMessages);

}
