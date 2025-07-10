package ai.agentscentral.core.session.state;

import java.util.Optional;

/**
 * ConversationStateManager
 *
 * @author Rizwan Idrees
 */
public interface SessionStateManager {

    Optional<SessionState> getCurrentState(String sessionId);

    SessionState updateState(SessionState newState);

}
