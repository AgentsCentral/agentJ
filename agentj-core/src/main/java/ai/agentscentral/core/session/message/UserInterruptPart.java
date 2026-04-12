package ai.agentscentral.core.session.message;

import java.util.List;

/**
 * A {@link MessagePart} carrying the user's response to a single tool interrupt, embedded
 * in a {@link UserMessage}.
 *
 * <p>For each {@link ToolInterruptPart} presented to the user, the application constructs
 * a corresponding {@code UserInterruptPart} containing the user-supplied values for the
 * declared {@link ToolInterruptParameter}s. The
 * {@link ai.agentscentral.core.agentic.executor.DefaultAgentExecutor} collects these parts
 * from the incoming {@link UserMessage}, matches them to pending tool calls by
 * {@code toolCallId}, and passes the {@link InterruptParameterValue}s to
 * {@link ai.agentscentral.core.tool.DefaultToolCallExecutor} to complete execution.</p>
 *
 * <p>Always reports {@link MessagePartType#user_interrupt} from {@link #type()}.</p>
 *
 * @param type                unused constructor parameter; {@link #type()} always returns
 *                            {@link MessagePartType#user_interrupt}
 * @param toolCallId          id of the tool call this response belongs to; correlates with
 *                            the original {@link ToolInterruptPart#toolCallId()}
 * @param interruptName       name of the interrupt being responded to; correlates with
 *                            {@link ToolInterruptPart#interruptName()}
 * @param interruptParameters the parameter values supplied by the user
 *
 * @author Rizwan Idrees
 */
public record UserInterruptPart(MessagePartType type,
                                String toolCallId,
                                String interruptName,
                                List<InterruptParameterValue> interruptParameters) implements MessagePart {

    /**
     * Always returns {@link MessagePartType#user_interrupt}.
     *
     * @return {@link MessagePartType#user_interrupt}
     */
    public MessagePartType type() {
        return MessagePartType.user_interrupt;
    }
}
