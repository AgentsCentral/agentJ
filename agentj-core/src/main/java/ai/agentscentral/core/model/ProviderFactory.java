package ai.agentscentral.core.model;

import ai.agentscentral.core.agent.Agent;
import ai.agentscentral.core.agent.AgentExecutor;

/**
 * ProviderFactory
 *
 * @param <P>
 * @param <C>
 * @author Rizwan Idrees
 */
public interface ProviderFactory<P extends AgentExecutor, C extends ProviderClient> {

    P createAgentExecutor(Agent agent);

}
