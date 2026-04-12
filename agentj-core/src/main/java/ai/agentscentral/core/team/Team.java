package ai.agentscentral.core.team;

import ai.agentscentral.core.agent.Agent;
import ai.agentscentral.core.agentic.Agentic;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * A team of {@link Agentic} entities coordinated by a designated leader agent.
 *
 * <p>A {@code Team} implements {@link Agentic}, so it can itself be a member of another
 * team, enabling nested team hierarchies. At initialization time,
 * {@link ai.agentscentral.core.agentic.executor.AgenticExecutorInitializer} inspects the
 * team's {@link TeamMode} and creates the appropriate
 * {@link ai.agentscentral.core.agentic.executor.TeamExecutor} — currently
 * {@link ai.agentscentral.core.agentic.executor.RoutingModeTeamExecutor} for
 * {@link TeamMode#route} — which eagerly initialises an executor for the leader and
 * each member.</p>
 *
 * <p>During execution, the active team member is determined by the
 * {@link ai.agentscentral.core.context.ContextState} stored in the
 * {@link ai.agentscentral.core.context.ContextStateManager}. If no state is recorded the
 * leader handles the request by default. Handoffs between members update the context
 * state so subsequent messages are routed to the correct agent.</p>
 *
 * @param name    unique name identifying this team; used for registration, handoff
 *                resolution, and context state tracking
 * @param leader  the default {@link Agent} that handles requests when no specific member
 *                is active and coordinates overall team behaviour
 * @param members the other {@link Agentic} entities (agents or nested teams) that belong
 *                to this team; may be empty if the team consists solely of the leader
 * @param mode    the coordination strategy that governs how requests are routed among
 *                team members; see {@link TeamMode}
 *
 * @author Rizwan Idrees
 */
public record Team(@Nonnull String name,
                   @Nonnull Agent leader,
                   @Nonnull List<Agentic> members,
                   @Nonnull TeamMode mode) implements Agentic {
}


