package ai.agentscentral.core.model;

import ai.agentscentral.core.agent.AgentExecutor;

/**
 * ModelConfig
 *
 * @author Rizwan Idrees
 */
public interface ModelConfig {

    ProviderFactory<? extends AgentExecutor, ? extends ProviderClient> getFactory();
}
