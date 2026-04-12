package ai.agentscentral.core.agentic.executor.register;

import ai.agentscentral.core.agentic.Agentic;
import ai.agentscentral.core.agentic.executor.AgenticExecutor;
import ai.agentscentral.core.team.Team;

/**
 * A {@link RegisteredAgentic} representing a {@link Team} that has been registered with
 * an {@link AgenticRegistrar}.
 *
 * @param team     the registered team
 * @param partOf   the parent team this team belongs to, or {@code null} if top-level
 * @param executor the executor associated with this team
 *
 * @author Rizwan Idrees
 */
public record RegisteredTeam(Team team, Team partOf, AgenticExecutor<? extends Agentic> executor) implements RegisteredAgentic {

}
