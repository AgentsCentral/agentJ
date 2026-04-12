package ai.agentscentral.core.agentic.executor;

import ai.agentscentral.core.team.Team;

/**
 * Execution contract for a {@link Team} of agents.
 *
 * <p>Extends {@link AgenticExecutor} with {@link Team} as the managed agentic type.
 * The concrete implementation depends on the team's {@link ai.agentscentral.core.team.TeamMode};
 * currently {@link RoutingModeTeamExecutor} is used for {@code TeamMode.route}.</p>
 *
 * @author Rizwan Idrees
 */
public interface TeamExecutor extends AgenticExecutor<Team> {

}
