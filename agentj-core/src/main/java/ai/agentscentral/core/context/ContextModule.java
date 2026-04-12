package ai.agentscentral.core.context;

import jakarta.annotation.Nonnull;

/**
 * Groups the context-management components required by the agentic execution framework.
 *
 * <p>Held inside {@link ai.agentscentral.core.agentic.AgenticModule} and supplied to the
 * executor infrastructure at session startup.  The {@link ContextManager} maintains the
 * per-context message history; the {@link ContextStateManager} tracks which agent or
 * team is currently active so that follow-up messages can be routed correctly.</p>
 *
 * <p>A default in-memory instance is available via {@link #defaultContextModule()}.
 * For persistent storage (e.g. MongoDB), provide a {@code ContextModule} whose components
 * are backed by the appropriate store.</p>
 *
 * @param contextManager      manages the per-context {@link ai.agentscentral.core.session.message.Message}
 *                            history; must not be {@code null}
 * @param contextStateManager tracks the currently active agent/team state for routing;
 *                            must not be {@code null}
 *
 * @author Rizwan Idrees
 */
public record ContextModule(@Nonnull ContextManager contextManager,
                            @Nonnull ContextStateManager contextStateManager) {

    /**
     * Creates a {@code ContextModule} using the default {@link InMemoryContextManager} and
     * {@link InMemoryContextStateManager}.
     *
     * <p>Suitable for single-node deployments and testing.  For clustered or durable
     * storage, construct a {@code ContextModule} manually with production-grade
     * implementations.</p>
     *
     * @return a new module backed entirely by in-memory data structures
     */
    public static ContextModule defaultContextModule(){
        return new ContextModule(new InMemoryContextManager(), new InMemoryContextStateManager());
    }
}
