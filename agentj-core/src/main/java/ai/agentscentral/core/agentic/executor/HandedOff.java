package ai.agentscentral.core.agentic.executor;

import ai.agentscentral.core.agentic.Agentic;

/**
 * Immutable result returned by a {@link HandoffExecutor} after a successful handoff.
 *
 * <p>Carries the name of the target agent and the executor that should drive the remainder
 * of the current conversational turn under that agent.</p>
 *
 * @param agent              name of the target agent that execution has been handed off to
 * @param newAgenticExecutor the executor for the target agentic entity; used to continue
 *                           the turn after the handoff
 *
 * @author Rizwan Idrees
 */
public record HandedOff(String agent, AgenticExecutor<? extends Agentic> newAgenticExecutor) {
}
