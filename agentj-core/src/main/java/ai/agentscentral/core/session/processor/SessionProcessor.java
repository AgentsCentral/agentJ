package ai.agentscentral.core.session.processor;

import ai.agentscentral.core.session.message.AssistantMessage;
import ai.agentscentral.core.session.message.UserMessage;
import ai.agentscentral.core.session.user.User;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.List;

/**
 * SessionProcessor
 *
 * @author Rizwan Idrees
 */
public interface SessionProcessor {

    List<AssistantMessage> process(@Nonnull String sessionId, @Nonnull UserMessage message, @Nullable User user);
}
