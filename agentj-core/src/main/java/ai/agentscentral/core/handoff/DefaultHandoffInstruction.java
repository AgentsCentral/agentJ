package ai.agentscentral.core.handoff;

import jakarta.annotation.Nonnull;

/**
 * Default immutable implementation of {@link HandoffInstruction}.
 *
 * <p>Created by the model provider layer when it parses a handoff call from an assistant
 * response, then attached to the corresponding
 * {@link ai.agentscentral.core.session.message.AssistantMessage} for processing by
 * {@link ai.agentscentral.core.agentic.executor.DefaultAgentExecutor}.</p>
 *
 * @param callId  the call identifier assigned by the model provider to this handoff
 * @param handoff the {@link Handoff} descriptor identifying the target agentic entity
 *
 * @author Rizwan Idrees
 */
public record DefaultHandoffInstruction(@Nonnull String callId, @Nonnull Handoff handoff) implements HandoffInstruction {
}
