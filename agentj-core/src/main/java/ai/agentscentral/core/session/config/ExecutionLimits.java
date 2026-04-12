package ai.agentscentral.core.session.config;

/**
 * Configures the per-message safety limits enforced during a conversational turn.
 *
 * <p>A new {@link ai.agentscentral.core.agentic.executor.MessageExecutionContext} is
 * created for every incoming user message and checked against these limits after each
 * tool-call round and each handoff. When a limit is exceeded the executor stops
 * recursing and returns the messages accumulated so far, preventing runaway tool loops
 * or infinite handoff chains.</p>
 *
 * @param maxToolCallsPerMessage maximum number of tool-call rounds allowed within a
 *                               single user message turn
 * @param maxHandOffsPerMessage  maximum number of handoffs allowed within a single
 *                               user message turn
 *
 * @author Rizwan Idrees
 */
public record ExecutionLimits(int maxToolCallsPerMessage,
                              int maxHandOffsPerMessage) {

    /**
     * Creates an {@code ExecutionLimits} instance with sensible defaults:
     * a maximum of 5 tool-call rounds and 5 handoffs per message.
     *
     * @return a new {@code ExecutionLimits} with {@code maxToolCallsPerMessage = 5}
     *         and {@code maxHandOffsPerMessage = 5}
     */
    public static ExecutionLimits defaultExecutionLimits(){
        return new ExecutionLimits(5, 5);
    }
}
