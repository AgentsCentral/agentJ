package ai.agentscentral.core.tool;

import java.util.Map;

/**
 * ToolCallInstruction
 *
 * @author Rizwan Idrees
 */
public interface ToolCallInstruction {

    String id();

    String rawArguments();

    Map<String, Object> arguments();

    ToolCall toolCall();
}
