package ai.agentscentral.core.agentic.executor;

import ai.agentscentral.core.session.config.ExecutionLimits;

/**
 * Tracks per-message execution state during a single conversational turn.
 *
 * <p>A new instance is created for every incoming user message and passed through the
 * executor chain. It counts tool calls and handoffs against the configured
 * {@link ExecutionLimits} and records whether any tool-call interrupts have already been
 * processed, preventing them from being re-raised on the same message.</p>
 *
 * @author Rizwan Idrees
 */
public class MessageExecutionContext {

    private int handOffCount = 0;

    private int toolCallsCount = 0;
    private boolean interruptsProcessed;
    private final ExecutionLimits limits;

    /**
     * Creates a new execution context with the given limits.
     *
     * @param limits the maximum allowed tool calls and handoffs per message
     */
    public MessageExecutionContext(ExecutionLimits limits) {
        this.limits = limits;
    }

    /**
     * Increments the handoff counter by one.
     * Called each time a handoff is performed within the current turn.
     */
    public void incrementHandOffCount() {
        handOffCount++;
    }

    /**
     * Increments the tool-call counter by one.
     * Called each time a round of tool calls is executed within the current turn.
     */
    public void incrementToolCalls() {
        toolCallsCount++;
    }

    /**
     * Returns {@code true} if tool-call interrupts have already been processed for this message.
     *
     * @return {@code true} once {@link #markInterruptsAsProcessed()} has been called
     */
    public boolean isInterruptsProcessed() {
        return interruptsProcessed;
    }

    /**
     * Marks tool-call interrupts as processed for this message, preventing them from
     * being raised again on subsequent recursive executions within the same turn.
     */
    public void markInterruptsAsProcessed() {
        this.interruptsProcessed = true;
    }

    /**
     * Returns {@code true} if the number of handoffs performed in this turn has exceeded
     * the configured maximum.
     *
     * @return {@code true} when the handoff limit is exceeded
     */
    public boolean isHandoffLimitExceeded(){
        return handOffCount > limits.maxHandOffsPerMessage();
    }

    /**
     * Returns {@code true} if the number of tool-call rounds performed in this turn has
     * exceeded the configured maximum.
     *
     * @return {@code true} when the tool-call limit is exceeded
     */
    public boolean isToolCallLimitExceeded(){
        return toolCallsCount > limits.maxToolCallsPerMessage();
    }


}
