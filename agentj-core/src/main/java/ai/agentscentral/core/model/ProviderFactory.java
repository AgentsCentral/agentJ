package ai.agentscentral.core.model;

import ai.agentscentral.core.agent.Agent;
import ai.agentscentral.core.factory.AgentJFactory;
import ai.agentscentral.core.provider.ProviderAgentExecutor;
import jakarta.annotation.Nonnull;

/**
 * ProviderFactory
 *
 * @param <P>
 * @author Rizwan Idrees
 */
public interface ProviderFactory<P extends ProviderAgentExecutor> {

    P createAgentExecutor(@Nonnull Agent agent, @Nonnull AgentJFactory agentJFactory);

}
