package ai.agentscentral.core.conversation.state;

import java.util.Optional;

/**
 * ConversationStateManager
 *
 * @author Rizwan Idrees
 */
public interface ConversationStateManager {

    Optional<ConversationState> getCurrentState(String conversationId);

    ConversationState updateState(ConversationState newState);

}
