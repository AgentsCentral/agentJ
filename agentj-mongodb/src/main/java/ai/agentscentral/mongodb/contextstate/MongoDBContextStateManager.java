package ai.agentscentral.mongodb.contextstate;

import ai.agentscentral.core.context.ContextState;
import ai.agentscentral.core.context.ContextStateManager;

import java.util.Optional;

/**
 * MongoDBContextStateManager
 *
 * @author Mustafa Bhuiyan
 * @author Rizwan Idrees
 */
public class MongoDBContextStateManager implements ContextStateManager {
    @Override
    public Optional<ContextState> getCurrentState(String sessionId) {
        return Optional.empty();
    }

    @Override
    public ContextState updateState(ContextState newState) {
        return null;
    }
}
