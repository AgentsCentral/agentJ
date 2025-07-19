package ai.agentscentral.core.agentic.executor.register;

import ai.agentscentral.core.agentic.Agentic;
import ai.agentscentral.core.agentic.executor.AgenticExecutor;
import ai.agentscentral.core.team.Team;

/**
 * RegisteredTeam
 *
 * @param team
 * @param partOf
 * @param executor
 * @author Rizwan Idrees
 */
public record RegisteredTeam(Team team, Team partOf, AgenticExecutor<? extends Agentic> executor) implements RegisteredAgentic {

}
