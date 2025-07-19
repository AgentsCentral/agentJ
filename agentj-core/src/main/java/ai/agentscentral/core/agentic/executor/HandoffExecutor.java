package ai.agentscentral.core.agentic.executor;

import ai.agentscentral.core.handoff.HandoffInstruction;
import jakarta.annotation.Nonnull;

public interface HandoffExecutor {

   HandedOff handoff(@Nonnull String contextId, @Nonnull HandoffInstruction instruction);

}
