package ai.agentscentral.core.agentic.executor;

import ai.agentscentral.core.agentic.Agentic;
import ai.agentscentral.core.agentic.executor.register.AgenticRegistrar;
import ai.agentscentral.core.agentic.executor.register.Registered;
import ai.agentscentral.core.context.ContextStateManager;
import ai.agentscentral.core.handoff.Handoff;
import ai.agentscentral.core.handoff.HandoffInstruction;
import jakarta.annotation.Nonnull;

import java.util.Optional;

/**
 * DefaultHandoffExecutor
 *
 * @author Rizwan Idrees
 */

public class DefaultHandoffExecutor implements HandoffExecutor {

    private final AgenticRegistrar agenticRegistrar;
    private final ContextStateManager stateManager;


    public DefaultHandoffExecutor(AgenticRegistrar agenticRegistrar, ContextStateManager stateManager) {
        this.agenticRegistrar = agenticRegistrar;
        this.stateManager = stateManager;
    }

    @Override
    public AgenticExecutor<? extends Agentic> handoff(@Nonnull HandoffInstruction instruction) {
        final String agentic = Optional.of(instruction).map(HandoffInstruction::handoff).map(Handoff::agenticName)
                .orElseThrow(() -> new UnsupportedOperationException("Cannot transfer undefined agent"));

        return agenticRegistrar.findAny(agentic).map(Registered::executor)
                .orElseThrow(() -> new UnsupportedOperationException("Cannot transfer to un-registered agent"));
    }
}
