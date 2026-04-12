package ai.agentscentral.core.session.message;

import java.util.List;
import java.util.Map;

/**
 * A {@link MessagePart} representing a single interrupt prompt within a
 * {@link ToolInterruptMessage}.
 *
 * <p>Built by {@link ai.agentscentral.core.agentic.executor.DefaultAgentExecutor} for each
 * {@link ai.agentscentral.core.annotation.Interrupt} declared on a pending tool call. The
 * UI layer uses {@code renderer}, {@code toolCallParameters}, and {@code preCallResults}
 * to render an appropriate prompt; the user fills in values for each
 * {@link ToolInterruptParameter} and returns them as a {@link UserInterruptPart}.</p>
 *
 * <p>Always reports {@link MessagePartType#tool_interrupt} from {@link #type()}.</p>
 *
 * @param type                unused constructor parameter; {@link #type()} always returns
 *                            {@link MessagePartType#tool_interrupt}
 * @param toolCallId          id of the tool call this interrupt belongs to; used to
 *                            correlate the user's response with the pending invocation
 * @param interruptName       name of the {@link ai.agentscentral.core.annotation.Interrupt}
 *                            that triggered this prompt
 * @param renderer            reference to the UI renderer used to display this prompt
 * @param toolCallParameters  resolved arguments of the tool call, provided as context for
 *                            the interrupt UI
 * @param preCallResults      results of any pre-interrupt calls executed before displaying
 *                            this prompt (see {@link ai.agentscentral.core.annotation.Interrupt#interruptPreCalls()})
 * @param interruptParameters the parameters the user must supply in their response
 *
 * @author Rizwan Idrees
 */
public record ToolInterruptPart(MessagePartType type,
                                String toolCallId,
                                String interruptName,
                                String renderer,
                                Map<String, Object> toolCallParameters,
                                List<InterruptPreCallResult> preCallResults,
                                List<ToolInterruptParameter> interruptParameters) implements MessagePart {

    /**
     * Always returns {@link MessagePartType#tool_interrupt}.
     *
     * @return {@link MessagePartType#tool_interrupt}
     */
    public MessagePartType type() {
        return MessagePartType.tool_interrupt;
    }
}
