package ai.agentscentral.core.tool;

import java.util.List;

/**
 * ToolInterrupt
 *
 * @param name
 * @param rendererReference
 * @param parameters
 */

public record ToolInterrupt(String name, String rendererReference, List<InterruptParameter> parameters) {
}
