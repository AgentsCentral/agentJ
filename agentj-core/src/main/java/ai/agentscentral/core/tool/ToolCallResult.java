package ai.agentscentral.core.tool;

/**
 * ToolCallResult
 *
 * @author Rizwan Idrees
 */
public interface ToolCallResult {

    ToolCallInstruction instruction();
    Object result();
    Class<?> returnType();

}
