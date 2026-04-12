package ai.agentscentral.core.context;

import java.util.Optional;

/**
 * Tracks which agent or team is currently active within a session.
 *
 * <p>In a multi-agent session the active executor can change as handoffs occur.
 * A {@code ContextStateManager} records the latest {@link ContextState} snapshot —
 * capturing the current team, agent, and the team membership — so that
 * {@link ai.agentscentral.core.agentic.executor.RoutingModeTeamExecutor} can route
 * incoming follow-up messages to the correct agent without re-evaluating the full
 * conversation history.</p>
 *
 * <p>Implementations must be thread-safe if sessions may be processed concurrently.</p>
 *
 * @author Rizwan Idrees
 */
public interface ContextStateManager {

    /**
     * Returns the most recently recorded state for the given session, if any.
     *
     * @param sessionId the unique session identifier
     * @return an {@link Optional} containing the current {@link ContextState},
     *         or {@link Optional#empty()} if no state has been recorded yet
     */
    Optional<ContextState> getCurrentState(String sessionId);

    /**
     * Replaces the current state with {@code newState} and returns it.
     *
     * <p>Called by the executor infrastructure after each handoff to reflect the
     * newly active agent or team.</p>
     *
     * @param newState the state to store; must not be {@code null}
     * @return the stored {@code newState} (for convenient chaining)
     */
    ContextState updateState(ContextState newState);

}
