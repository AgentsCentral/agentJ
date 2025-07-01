package ai.agentscentral.core.conversation;

import ai.agentscentral.core.conversation.message.AssistantMessage;
import ai.agentscentral.core.conversation.message.UserMessage;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.List;

/**
 * ConversationProcessor
 *
 * @author Rizwan Idrees
 */
public interface ConversationProcessor {

    List<AssistantMessage> process(@Nullable String conversationId, @Nonnull UserMessage message);
}
