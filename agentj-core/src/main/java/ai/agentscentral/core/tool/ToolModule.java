package ai.agentscentral.core.tool;

import ai.agentscentral.core.agentic.executor.register.DefaultInterruptPreCallRegistrar;
import ai.agentscentral.core.session.message.ToolMessage;
import jakarta.annotation.Nonnull;

public record ToolModule(@Nonnull ToolsExtractor toolsExtractor,
                         @Nonnull ToolCallExecutor<ToolMessage> toolCallExecutor) {

    public static ToolModule defaultToolModule(){
        return new ToolModule(new ToolBagToolsExtractor(),
                new DefaultToolCallExecutor<>(new DefaultInterruptPreCallRegistrar()));
    }
}
