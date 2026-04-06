package ai.agentscentral.core.context;

import jakarta.annotation.Nonnull;

public record ContextModule(@Nonnull ContextManager contextManager,
                            @Nonnull ContextStateManager contextStateManager) {

    public static ContextModule defaultContextModule(){
        return new ContextModule(new InMemoryContextManager(), new InMemoryContextStateManager());
    }
}
