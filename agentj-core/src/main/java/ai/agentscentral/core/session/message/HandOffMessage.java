package ai.agentscentral.core.session.message;

/**
 * Records a handoff transition in the conversation context.
 *
 * <p>Created by {@link ai.agentscentral.core.agentic.executor.DefaultAgentExecutor}
 * when an {@link AssistantMessage} contains a handoff instruction. The message is
 * persisted before the provider is called again, giving the model visibility of the
 * handoff event. Its {@code parts} carry a {@link TextPart} with the handoff description
 * so the model understands why control was transferred.</p>
 *
 * @param contextId  the conversation context identifier
 * @param messageId  unique identifier for this message
 * @param handOffId  the call id of the original {@link ai.agentscentral.core.handoff.HandoffInstruction}
 * @param agentName  name of the target agent or team that execution was handed off to
 * @param parts      content parts describing the handoff (typically a single {@link TextPart})
 * @param timestamp  epoch-millisecond creation timestamp
 *
 * @author Rizwan Idrees
 */
public record HandOffMessage(String contextId,
                             String messageId,
                             String handOffId,
                             String agentName,
                             MessagePart[] parts,
                             long timestamp) implements Message {
}
