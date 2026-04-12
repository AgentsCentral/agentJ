package ai.agentscentral.core.team;

/**
 * Defines the coordination strategy used by a {@link Team} to route incoming requests
 * among its members.
 *
 * <p>The mode is set on the {@link Team} at construction time and inspected by
 * {@link ai.agentscentral.core.agentic.executor.AgenticExecutorInitializer} to select
 * the appropriate {@link ai.agentscentral.core.agentic.executor.TeamExecutor}
 * implementation.</p>
 *
 * @author Rizwan Idrees
 */
public enum TeamMode {

    /**
     * Routes each request to the team member whose name matches the
     * {@link ai.agentscentral.core.context.ContextState#currentAgent()} recorded in the
     * conversation state. Falls back to the team leader when no state is present.
     *
     * <p>Implemented by
     * {@link ai.agentscentral.core.agentic.executor.RoutingModeTeamExecutor}.</p>
     */
    route
}
