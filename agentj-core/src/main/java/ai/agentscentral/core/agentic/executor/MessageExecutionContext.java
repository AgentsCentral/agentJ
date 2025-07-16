package ai.agentscentral.core.agentic.executor;

import ai.agentscentral.core.session.config.ExecutionLimits;

/**
 * MessageContext
 *
 * @author Rizwan
 */
public class MessageExecutionContext {

    private final ExecutionLimits limits;

    public MessageExecutionContext(ExecutionLimits limits) {
        this.limits = limits;
    }
    private int handOffCount = 0;

    private int toolCallsCount = 0;

    public int getHandOffCount() {
        return handOffCount;
    }

    public int getToolCallsCount() {
        return toolCallsCount;
    }

    public void incrementHandOffCount() {
        handOffCount++;
    }

    public void incrementToolCalls() {
        toolCallsCount++;
    }

    public boolean isHandoffLimitExceeded(){
        return handOffCount > limits.maxHandOffsPerMessage();
    }

    public boolean isToolCallLimitExceeded(){
        return toolCallsCount > limits.maxToolCallsPerMessage();
    }

}
