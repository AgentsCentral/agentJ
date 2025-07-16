package ai.agentscentral.core.context;

import java.util.Optional;

/**
 * InMemorySessionStateManager
 *
 * @author Rizwan Idrees
 */
public class InMemoryContextStateManager implements ContextStateManager {

    private ContextState currentState;

    @Override
    public Optional<ContextState> getCurrentState(String sessionId) {
        return Optional.ofNullable(currentState);
    }

    @Override
    public ContextState updateState(ContextState newState) {
        this.currentState = newState;
        return newState;
    }
}
