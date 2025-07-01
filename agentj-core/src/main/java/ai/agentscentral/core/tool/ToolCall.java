package ai.agentscentral.core.tool;

import java.lang.reflect.Method;
import java.util.List;

/**
 * ToolCall
 *
 * @param toolBag
 * @param method
 * @param name
 * @param description
 * @param parameters
 * @author Rizwan Idrees
 */
public record ToolCall(ToolBag toolBag, Method method, String name, String description,
                       List<ToolParameter> parameters) {
}
