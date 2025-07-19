package ai.agentscentral.core.agentic.executor.register;

import ai.agentscentral.core.agentic.Agentic;
import ai.agentscentral.core.agentic.executor.AgenticExecutor;

/**
 * Registered
 *
 * @author Rizwan Idrees
 */
public interface RegisteredAgentic {

    AgenticExecutor<? extends Agentic> executor();

}
