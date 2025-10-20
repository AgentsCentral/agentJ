package ai.agentscentral.core.agentic.executor;

import ai.agentscentral.core.agentic.Agentic;

/**
 * HandedOff
 *
 * @param agent
 * @param newAgenticExecutor
 * @author Rizwan Idrees
 */
public record HandedOff(String agent, AgenticExecutor<? extends Agentic> newAgenticExecutor) {
}
