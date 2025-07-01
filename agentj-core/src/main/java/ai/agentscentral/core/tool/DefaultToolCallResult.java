package ai.agentscentral.core.tool;

/**
 * DefaultToolCallResult
 * @param instruction
 * @param result
 * @param returnType
 *
 * @author Rizwan Idrees
 */
public record DefaultToolCallResult(ToolCallInstruction instruction, Object result, Class<?> returnType) implements ToolCallResult {
}
