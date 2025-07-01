package ai.agentscentral.core.conversation;

/**
 * MessageContext
 *
 * @author Rizwan
 */
class MessageContext {

    private int handOffCount = 0;
    private int toolCallsCount = 0;

    int getHandOffCount() {
        return handOffCount;
    }

    int getToolCallsCount() {
        return toolCallsCount;
    }

    void incrementHandOffCount() {
        handOffCount++;
    }

    void incrementToolCalls() {
        handOffCount++;
    }

}
