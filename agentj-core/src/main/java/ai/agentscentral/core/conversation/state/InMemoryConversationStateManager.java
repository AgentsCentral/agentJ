package ai.agentscentral.core.conversation.state;

import java.util.Optional;

/**
 * InMemoryConversationStateManager
 *
 * @author Rizwan Idrees
 */
public class InMemoryConversationStateManager implements ConversationStateManager {

    private ConversationState currentState;

    @Override
    public Optional<ConversationState> getCurrentState(String conversationId) {
        return Optional.ofNullable(currentState);
    }

    @Override
    public ConversationState updateState(ConversationState newState) {
        this.currentState = newState;
        return newState;
    }
}
