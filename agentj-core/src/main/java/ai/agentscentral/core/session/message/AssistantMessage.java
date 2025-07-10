package ai.agentscentral.core.session.message;

import ai.agentscentral.core.handoff.HandoffInstruction;
import ai.agentscentral.core.tool.ToolCallInstruction;

import java.util.List;
import java.util.Objects;

/**
 * AssistantMessage
 *
 * @param contextId
 * @param messageId
 * @param parts
 * @param toolCalls
 * @param handoffs
 * @param timestamp
 *
 * @author Rizwan Idrees
 */
public record AssistantMessage(String contextId,
                               String messageId,
                               MessagePart[] parts,
                               List<ToolCallInstruction> toolCalls,
                               List<HandoffInstruction> handoffs,
                               long timestamp) implements Message {


    public boolean hasToolCalls(){
        return Objects.nonNull(toolCalls) && !toolCalls.isEmpty();
    }

    public boolean hasHandOffs(){
        return Objects.nonNull(handoffs) && !handoffs.isEmpty();
    }

}
