package ai.agentscentral.core.tool;

import java.util.Map;

/**
 * DefaultToolCallInstruction
 *
 * @param id
 * @param name
 * @param rawArguments
 * @param arguments
 * @author Rizwan Idrees
 */
public record DefaultToolCallInstruction(String id,
                                         String name,
                                         String rawArguments,
                                         Map<String, Object> arguments)
        implements ToolCallInstruction {
}
