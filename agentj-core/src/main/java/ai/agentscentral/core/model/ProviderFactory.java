package ai.agentscentral.core.model;

import ai.agentscentral.core.agentic.Agentic;
import ai.agentscentral.core.agentic.executor.AgenticExecutor;

/**
 * ProviderFactory
 *
 * @param <P>
 * @param <C>
 * @author Rizwan Idrees
 */
public interface ProviderFactory<P extends AgenticExecutor, C extends ProviderClient> {

    P createAgentExecutor(Agentic agentic);

}
