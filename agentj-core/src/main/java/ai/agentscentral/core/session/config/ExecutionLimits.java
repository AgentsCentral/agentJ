package ai.agentscentral.core.session.config;

/**
 * ExecutionLimits
 *
 * @param maxToolCallsPerMessage
 * @param maxHandOffsPerMessage
 *
 * @author Rizwan Idrees
 */
public record ExecutionLimits(int maxToolCallsPerMessage,
                              int maxHandOffsPerMessage) {


    public static ExecutionLimits defaultExecutionLimits(){
        return new ExecutionLimits(5, 5);
    }
}
