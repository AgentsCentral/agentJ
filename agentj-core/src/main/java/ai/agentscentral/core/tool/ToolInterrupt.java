package ai.agentscentral.core.tool;

import java.util.List;

/**
 * ToolInterrupt
 *
 * @param name
 * @param renderer
 * @param preInterruptCalls
 * @param parameters
 */

public record ToolInterrupt(String name,
                            String renderer,
                            List<String> preInterruptCalls,
                            List<InterruptParameter> parameters) {
}
