package ai.agentscentral.core.agentic.executor;

import ai.agentscentral.core.session.config.ExecutionLimits;

/**
 * MessageContext
 *
 * @author Rizwan
 */
public class MessageExecutionContext {

    private int handOffCount = 0;

    private int toolCallsCount = 0;
    private boolean interruptsProcessed;
    private final ExecutionLimits limits;

    public MessageExecutionContext(ExecutionLimits limits) {
        this.limits = limits;
    }

    public void incrementHandOffCount() {
        handOffCount++;
    }

    public void incrementToolCalls() {
        toolCallsCount++;
    }

    public boolean isInterruptsProcessed() {
        return interruptsProcessed;
    }

    public void markInterruptsAsProcessed() {
        this.interruptsProcessed = true;
    }

    public boolean isHandoffLimitExceeded(){
        return handOffCount > limits.maxHandOffsPerMessage();
    }

    public boolean isToolCallLimitExceeded(){
        return toolCallsCount > limits.maxToolCallsPerMessage();
    }


}
