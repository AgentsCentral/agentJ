package ai.agentscentral.core.session.state;

import java.util.Optional;

/**
 * InMemoryConversationStateManager
 *
 * @author Rizwan Idrees
 */
public class InMemoryConversationStateManager implements SessionStateManager {

    private SessionState currentState;

    @Override
    public Optional<SessionState> getCurrentState(String conversationId) {
        return Optional.ofNullable(currentState);
    }

    @Override
    public SessionState updateState(SessionState newState) {
        this.currentState = newState;
        return newState;
    }
}
