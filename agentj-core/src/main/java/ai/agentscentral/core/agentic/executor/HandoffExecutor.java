package ai.agentscentral.core.agentic.executor;

import ai.agentscentral.core.handoff.HandoffInstruction;
import jakarta.annotation.Nonnull;

/**
 * Resolves a handoff instruction and transitions execution to the target agentic entity.
 *
 * <p>Implementations look up the target agent or team by name in the
 * {@link ai.agentscentral.core.agentic.executor.register.AgenticRegistrar}, update the
 * conversation's {@link ai.agentscentral.core.context.ContextState}, and return a
 * {@link HandedOff} descriptor that carries the new executor so the caller can continue
 * the turn under the new entity.</p>
 *
 * @author Rizwan Idrees
 */
public interface HandoffExecutor {

    /**
     * Performs the handoff described by {@code instruction}, switching the active agentic
     * entity for the given context.
     *
     * @param contextId   the conversation context identifier
     * @param instruction the handoff instruction containing the target agentic name and call id
     * @return a {@link HandedOff} record holding the target agent name and its executor
     * @throws UnsupportedOperationException if the target agentic is not registered or the
     *                                        instruction does not specify a valid target
     */
   HandedOff handoff(@Nonnull String contextId, @Nonnull HandoffInstruction instruction);

}
