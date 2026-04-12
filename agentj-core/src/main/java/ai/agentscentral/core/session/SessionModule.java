package ai.agentscentral.core.session;

import ai.agentscentral.core.session.id.DefaultMessageIdGenerator;
import ai.agentscentral.core.session.id.DefaultSessionIdGenerator;
import ai.agentscentral.core.session.id.MessageIdGenerator;
import ai.agentscentral.core.session.id.SessionIdGenerator;
import jakarta.annotation.Nonnull;

/**
 * Groups the session identity components required by the agentic execution framework.
 *
 * <p>Held inside {@link ai.agentscentral.core.agentic.AgenticModule} and consumed by
 * {@link ai.agentscentral.core.agentic.executor.DefaultAgentExecutor} to generate unique
 * identifiers for each message produced during a turn. A default instance backed by
 * UUID-based generators is available via {@link #defaultSessionModule()}.</p>
 *
 * @param sessionIdGenerator generator for unique conversation (session) identifiers
 * @param messageIdGenerator generator for unique message identifiers within a session
 *
 * @author Rizwan Idrees
 */
public record SessionModule(@Nonnull SessionIdGenerator sessionIdGenerator,
                            @Nonnull MessageIdGenerator messageIdGenerator) {

    /**
     * Creates a {@code SessionModule} using the default UUID-based
     * {@link DefaultSessionIdGenerator} and {@link DefaultMessageIdGenerator}.
     *
     * @return a new module with standard id generators
     */
    public static SessionModule defaultSessionModule(){
        return new SessionModule(new DefaultSessionIdGenerator(), new DefaultMessageIdGenerator());
    }
}
