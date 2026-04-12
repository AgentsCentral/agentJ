package ai.agentscentral.core.session.message;

import ai.agentscentral.core.handoff.HandoffInstruction;
import ai.agentscentral.core.tool.ToolCallInstruction;

import java.util.List;
import java.util.Objects;

/**
 * Represents a response produced by the LLM provider during a conversational turn.
 *
 * <p>An {@code AssistantMessage} is created by the provider layer and may carry one or
 * more of: plain text content (via {@link TextPart}s in {@code parts}), tool call
 * instructions requesting tool execution, or handoff instructions requesting a transfer
 * to another agent or team. {@link ai.agentscentral.core.agentic.executor.DefaultAgentExecutor}
 * inspects these fields to decide the next step in the turn.</p>
 *
 * @param contextId  the conversation context identifier
 * @param messageId  unique identifier for this message
 * @param parts      text or thinking content parts produced by the model
 * @param toolCalls  tool call instructions emitted by the model; may be {@code null} or empty
 * @param handoffs   handoff instructions emitted by the model; may be {@code null} or empty
 * @param timestamp  epoch-millisecond creation timestamp
 *
 * @author Rizwan Idrees
 */
public record AssistantMessage(String contextId,
                               String messageId,
                               MessagePart[] parts,
                               List<ToolCallInstruction> toolCalls,
                               List<HandoffInstruction> handoffs,
                               long timestamp) implements Message {

    /**
     * Returns {@code true} if this message contains at least one tool call instruction.
     *
     * @return {@code true} when the model requested one or more tool invocations
     */
    public boolean hasToolCalls(){
        return Objects.nonNull(toolCalls) && !toolCalls.isEmpty();
    }

    /**
     * Returns {@code true} if this message contains at least one handoff instruction.
     *
     * @return {@code true} when the model requested a handoff to another agent or team
     */
    public boolean hasHandOffs(){
        return Objects.nonNull(handoffs) && !handoffs.isEmpty();
    }

}
