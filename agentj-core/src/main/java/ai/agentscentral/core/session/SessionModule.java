package ai.agentscentral.core.session;

import ai.agentscentral.core.session.id.DefaultMessageIdGenerator;
import ai.agentscentral.core.session.id.DefaultSessionIdGenerator;
import ai.agentscentral.core.session.id.MessageIdGenerator;
import ai.agentscentral.core.session.id.SessionIdGenerator;
import jakarta.annotation.Nonnull;

public record SessionModule(@Nonnull SessionIdGenerator sessionIdGenerator,
                            @Nonnull MessageIdGenerator messageIdGenerator) {


    public static SessionModule defaultSessionModule(){
        return new SessionModule(new DefaultSessionIdGenerator(), new DefaultMessageIdGenerator());
    }
}
