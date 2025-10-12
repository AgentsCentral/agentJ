package ai.agentscentral.core.tool;

import java.util.List;

/**
 * ToolInterrupt
 *
 * @param name
 * @param renderer
 * @param parameters
 */

public record ToolInterrupt(String name, String renderer, List<InterruptParameter> parameters) {
}
