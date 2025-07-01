package ai.agentscentral.core.handoff;

/**
 * DefaultHandoffInstruction
 *
 * @param callId
 * @param handoff
 */
public record DefaultHandoffInstruction(String callId, Handoff handoff) implements HandoffInstruction {
}
