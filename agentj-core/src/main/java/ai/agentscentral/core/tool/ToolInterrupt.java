package ai.agentscentral.core.tool;

import ai.agentscentral.core.interrupt.InterruptType;

import java.util.Map;

/**
 * ToolInterrupt
 *
 * @param type
 * @param rendererReference
 * @param parameters
 */

public record ToolInterrupt(InterruptType type,
                            String rendererReference,
                            Map<String, String> parameters) {
}
