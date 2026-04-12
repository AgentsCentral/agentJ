package ai.agentscentral.core.agentic.executor;

import ai.agentscentral.core.agent.Agent;

/**
 * Execution contract for a single {@link Agent}.
 *
 * <p>Extends {@link AgenticExecutor} with {@link Agent} as the managed agentic type.
 * The default implementation is {@link DefaultAgentExecutor}.</p>
 *
 * @author Rizwan Idrees
 */
public interface AgentExecutor extends AgenticExecutor<Agent> {
}
