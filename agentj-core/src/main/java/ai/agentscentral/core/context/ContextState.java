package ai.agentscentral.core.context;

/**
 * A snapshot of the active routing state for a conversation context.
 *
 * <p>When a session involves multiple agents and/or teams, handoffs transfer control from
 * one executor to another.  A {@code ContextState} captures which team and agent hold
 * execution at a given point in time, allowing the
 * {@link ai.agentscentral.core.agentic.executor.RoutingModeTeamExecutor} to resume
 * processing with the correct executor on subsequent turns without re-evaluating the
 * full message history.</p>
 *
 * <p>The canonical implementation is {@link DefaultContextState}.</p>
 *
 * @author Rizwan Idrees
 */
public interface ContextState {

    /**
     * Returns the unique identifier of the conversation context this state belongs to.
     *
     * @return the context ID; never {@code null}
     */
    String contextId();

    /**
     * Returns the name of the team that is currently executing, or {@code null} if the
     * active executor is a standalone agent not part of any team.
     *
     * @return the current team name, or {@code null}
     */
    String currentTeam();

    /**
     * Returns the name of the agent that is currently executing within the team (or as a
     * standalone agent).
     *
     * @return the current agent name, or {@code null} if not yet determined
     */
    String currentAgent();

    /**
     * Returns the name of the parent team that the current agent belongs to, enabling
     * nested-team routing.  May be {@code null} if the agent is at the top level of the
     * team hierarchy or is a standalone agent.
     *
     * @return the parent team name, or {@code null}
     */
    String partOfTeam();

}
