package ai.agentscentral.core.session.message;

import java.util.Map;

/**
 * A user-submitted message that carries the responses to one or more pending tool
 * interrupts, resuming a paused tool-call execution.
 *
 * <p>After a {@link ToolInterruptMessage} is presented to the user, the application
 * submits a {@code UserInterruptMessage} (typically as a {@link UserMessage} whose
 * {@code parts} contain {@link UserInterruptPart}s). The
 * {@link ai.agentscentral.core.agentic.executor.DefaultAgentExecutor} detects the
 * interrupt parts and routes execution to
 * {@code resumeToolCallsWithInterrupt}, which extracts the parameter values and
 * completes the pending tool invocations.</p>
 *
 * @param contextId                the conversation context identifier
 * @param messageId                unique identifier for this message
 * @param interruptParameterValues flat map of interrupt parameter name to user-supplied
 *                                 value, providing a convenience view alongside {@code parts}
 * @param parts                    {@link UserInterruptPart}s containing the structured
 *                                 per-interrupt responses
 * @param timestamp                epoch-millisecond creation timestamp
 *
 * @author Rizwan Idrees
 */
public record UserInterruptMessage(String contextId,
                                   String messageId,
                                   Map<String, String> interruptParameterValues,
                                   MessagePart[] parts,
                                   long timestamp) implements Message {

}
