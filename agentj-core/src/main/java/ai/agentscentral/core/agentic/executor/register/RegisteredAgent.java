package ai.agentscentral.core.agentic.executor.register;

import ai.agentscentral.core.agent.Agent;
import ai.agentscentral.core.agentic.Agentic;
import ai.agentscentral.core.agentic.executor.AgenticExecutor;
import ai.agentscentral.core.team.Team;

/**
 * A {@link RegisteredAgentic} representing an individual {@link Agent} that has been
 * registered with an {@link AgenticRegistrar}.
 *
 * @param agent    the registered agent
 * @param partOf   the team this agent belongs to, or {@code null} if the agent is top-level
 * @param executor the executor associated with this agent
 *
 * @author Rizwan Idrees
 */
public record RegisteredAgent(Agent agent, Team partOf, AgenticExecutor<? extends Agentic> executor) implements RegisteredAgentic {
}
