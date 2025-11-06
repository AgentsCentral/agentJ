package ai.agentscentral.core.provider;

import ai.agentscentral.core.session.message.AssistantMessage;
import ai.agentscentral.core.session.message.Message;
import ai.agentscentral.core.session.user.User;

import java.util.List;

/**
 * ProviderAgentExecutor
 *
 * @author Rizwan Idrees
 */
public interface ProviderAgentExecutor {

    List<AssistantMessage> execute(String contextId, User user, List<Message> messages);
}
