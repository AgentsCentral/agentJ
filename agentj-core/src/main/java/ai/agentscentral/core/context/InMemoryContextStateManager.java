package ai.agentscentral.core.context;

import java.util.Optional;

/**
 * In-memory {@link ContextStateManager} that holds a single {@link ContextState} instance.
 *
 * <p>State is stored in a plain instance field; the {@code sessionId} parameter of
 * {@link #getCurrentState} is accepted but not used as this implementation is designed
 * for single-session use (one {@code InMemoryContextStateManager} per session).  For
 * multi-session or persistent state tracking, supply a custom implementation backed by
 * an external store.</p>
 *
 * <p>This implementation is suitable for in-process testing and single-node deployments.
 * It is not thread-safe for concurrent state updates.</p>
 *
 * @author Rizwan Idrees
 */
public class InMemoryContextStateManager implements ContextStateManager {

    private ContextState currentState;

    /**
     * {@inheritDoc}
     *
     * <p>The {@code sessionId} parameter is accepted but unused; always returns the
     * single stored state regardless of session identity.</p>
     */
    @Override
    public Optional<ContextState> getCurrentState(String sessionId) {
        return Optional.ofNullable(currentState);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Overwrites the previously stored state with {@code newState}.</p>
     */
    @Override
    public ContextState updateState(ContextState newState) {
        this.currentState = newState;
        return newState;
    }
}
