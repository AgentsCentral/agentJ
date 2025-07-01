package ai.agentscentral.core.conversation.state;

import java.util.Optional;

/**
 * InMemoryConversationStateManager
 *
 * @author Rizwan Idrees
 */
public class InMemoryConversationStateManager implements ConversationStateManager {


    @Override
    public Optional<ConversationState> getCurrentState(String conversationId) {
        return Optional.empty();
    }

    @Override
    public ConversationState updateState(ConversationState newState) {
        return null;
    }
}
