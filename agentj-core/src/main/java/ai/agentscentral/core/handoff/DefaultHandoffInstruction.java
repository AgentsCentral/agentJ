package ai.agentscentral.core.handoff;

import jakarta.annotation.Nonnull;

/**
 * DefaultHandoffInstruction
 *
 * @param callId
 * @param handoff
 */
public record DefaultHandoffInstruction(@Nonnull String callId, @Nonnull Handoff handoff) implements HandoffInstruction {
}
