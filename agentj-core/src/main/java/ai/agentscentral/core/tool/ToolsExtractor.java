package ai.agentscentral.core.tool;

import java.util.List;
import java.util.Map;

/**
 * ToolsExtractor
 *
 * @author Rizwan Idrees
 */
public interface ToolsExtractor {

    Map<String, ToolCall> extractTools(List<ToolBag> toolBags);
}
