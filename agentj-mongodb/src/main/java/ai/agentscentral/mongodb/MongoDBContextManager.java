package ai.agentscentral.mongodb;

import ai.agentscentral.core.context.ContextManager;
import ai.agentscentral.core.session.message.Message;

import java.util.List;

/**
 * MongoDBContextManager
 *
 * @author Mustafa Kamal
 * @author Rizwan Idrees
 */
public class MongoDBContextManager implements ContextManager {


    @Override
    public List<Message> getContext(String contextId) {
        return null;
    }

    @Override
    public List<Message> addContext(String contextId, List<? extends Message> newMessages) {
        return null;
    }
}
