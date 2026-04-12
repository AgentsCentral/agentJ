package ai.agentscentral.core.agentic.executor.register;

import ai.agentscentral.core.agentic.Agentic;
import ai.agentscentral.core.agentic.executor.AgenticExecutor;

/**
 * Represents an {@link Agentic} entity that has been registered with an
 * {@link AgenticRegistrar} along with its associated executor.
 *
 * <p>Concrete implementations are {@link RegisteredAgent} (for individual agents) and
 * {@link RegisteredTeam} (for teams). The sealed-style {@code switch} pattern in
 * {@link ai.agentscentral.core.agentic.executor.DefaultHandoffExecutor} dispatches on
 * these subtypes to determine how to update context state after a handoff.</p>
 *
 * @author Rizwan Idrees
 */
public interface RegisteredAgentic {

    /**
     * Returns the executor associated with this registered agentic entity.
     *
     * @return the executor; never {@code null}
     */
    AgenticExecutor<? extends Agentic> executor();

}
