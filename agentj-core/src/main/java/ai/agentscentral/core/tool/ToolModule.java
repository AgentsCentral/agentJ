package ai.agentscentral.core.tool;

import ai.agentscentral.core.agentic.executor.register.DefaultInterruptPreCallRegistrar;
import ai.agentscentral.core.session.message.ToolMessage;
import jakarta.annotation.Nonnull;

/**
 * Groups the tool infrastructure components required by the agentic execution framework.
 *
 * <p>Held inside {@link ai.agentscentral.core.agentic.AgenticModule} and consumed by
 * {@link ai.agentscentral.core.agentic.executor.DefaultAgentExecutor} at agent
 * initialisation. The extractor discovers tools from {@link ToolBag} instances; the
 * executor invokes them during the tool-call loop. A default instance is available via
 * {@link #defaultToolModule()}.</p>
 *
 * @param toolsExtractor  reflects over {@link ToolBag} instances to build the agent's
 *                        {@link ToolCall} map
 * @param toolCallExecutor invokes tool methods and converts results or errors to
 *                        {@link ToolMessage}s
 *
 * @author Rizwan Idrees
 */
public record ToolModule(@Nonnull ToolsExtractor toolsExtractor,
                         @Nonnull ToolCallExecutor<ToolMessage> toolCallExecutor) {

    /**
     * Creates a {@code ToolModule} using the default {@link ToolBagToolsExtractor} and a
     * {@link DefaultToolCallExecutor} backed by a
     * {@link ai.agentscentral.core.agentic.executor.register.DefaultInterruptPreCallRegistrar}.
     *
     * @return a new module with standard tool extraction and execution components
     */
    public static ToolModule defaultToolModule(){
        return new ToolModule(new ToolBagToolsExtractor(),
                new DefaultToolCallExecutor<>(new DefaultInterruptPreCallRegistrar()));
    }
}
