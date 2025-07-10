package ai.agentscentral.core.session.config;

/**
 * MessageLimits
 *
 * @param maxToolCallsPerMessage
 * @param maxHandOffsPerMessage
 *
 * @author Rizwan Idrees
 */
public record MessageLimits(int maxToolCallsPerMessage,
                            int maxHandOffsPerMessage) {


    public static MessageLimits defaultMessageLimits(){
        return new MessageLimits(5, 5);
    }
}
