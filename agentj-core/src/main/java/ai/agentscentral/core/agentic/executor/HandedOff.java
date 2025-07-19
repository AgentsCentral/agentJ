package ai.agentscentral.core.agentic.executor;

import ai.agentscentral.core.agentic.Agentic;

public record HandedOff(String agent, AgenticExecutor<? extends Agentic> newAgenticExecutor) {
}
