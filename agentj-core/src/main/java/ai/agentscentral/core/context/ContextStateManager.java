package ai.agentscentral.core.context;

import java.util.Optional;

/**
 * ContextStateManager
 *
 * @author Rizwan Idrees
 */
public interface ContextStateManager {

    Optional<ContextState> getCurrentState(String sessionId);

    ContextState updateState(ContextState newState);

}
