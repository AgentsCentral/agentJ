package ai.agentscentral.core.model;

import ai.agentscentral.core.agentic.executor.AgenticExecutor;

/**
 * ModelConfig
 *
 * @author Rizwan Idrees
 */
public interface ModelConfig {

    ProviderFactory<? extends AgenticExecutor, ? extends ProviderClient> getFactory();
}
