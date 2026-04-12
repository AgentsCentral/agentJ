package ai.agentscentral.core.tool;

import java.util.List;
import java.util.Map;

/**
 * Extracts {@link ToolCall} descriptors from a list of {@link ToolBag} instances.
 *
 * <p>Called at agent initialisation time by
 * {@link ai.agentscentral.core.agentic.executor.DefaultAgentExecutor} to build the
 * tool map passed to both the model provider (for schema generation) and
 * {@link DefaultToolCallExecutor} (for invocation). The default implementation is
 * {@link ToolBagToolsExtractor}.</p>
 *
 * @author Rizwan Idrees
 */
public interface ToolsExtractor {

    /**
     * Reflects over the given {@link ToolBag} instances and returns a map from tool name
     * to {@link ToolCall} descriptor.
     *
     * @param toolBags the tool bags to inspect; may be {@code null} or empty
     * @return a map keyed by {@link ToolCall#name()}; empty if {@code toolBags} is
     *         {@code null} or empty
     */
    Map<String, ToolCall> extractTools(List<ToolBag> toolBags);
}
