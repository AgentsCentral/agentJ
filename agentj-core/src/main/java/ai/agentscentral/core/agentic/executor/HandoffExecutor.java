package ai.agentscentral.core.agentic.executor;

import ai.agentscentral.core.agentic.Agentic;
import ai.agentscentral.core.handoff.HandoffInstruction;

public interface HandoffExecutor {

    AgenticExecutor<? extends Agentic> handoff(HandoffInstruction instruction);

}
