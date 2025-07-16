package ai.agentscentral.core.model;

import ai.agentscentral.core.provider.ProviderAgentExecutor;

/**
 * ModelConfig
 *
 * @author Rizwan Idrees
 */
public interface ModelConfig {

    ProviderFactory<? extends ProviderAgentExecutor> getFactory();
}
