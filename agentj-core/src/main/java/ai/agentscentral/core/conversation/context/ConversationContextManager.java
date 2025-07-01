package ai.agentscentral.core.conversation.context;

import ai.agentscentral.core.conversation.message.Message;

import java.util.List;

/**
 * ConversationContextManager
 *
 * @author Rizwan Idrees
 */
public interface ConversationContextManager {


    List<Message> getContext(String contextId);
    List<Message> addContext(String contextId, List<? extends Message> newMessages);

}
