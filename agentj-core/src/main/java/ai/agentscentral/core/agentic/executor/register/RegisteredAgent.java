package ai.agentscentral.core.agentic.executor.register;

import ai.agentscentral.core.agent.Agent;
import ai.agentscentral.core.agentic.Agentic;
import ai.agentscentral.core.agentic.executor.AgenticExecutor;
import ai.agentscentral.core.team.Team;

/**
 * RegisteredAgent
 *
 * @param agent
 * @param partOf
 * @param executor
 * @author Rizwan Idrees
 */
record RegisteredAgent(Agent agent, Team partOf, AgenticExecutor<? extends Agentic> executor) implements Registered {
}
