package ai.agentscentral.core.agent;

import ai.agentscentral.core.conversation.message.AssistantMessage;
import ai.agentscentral.core.conversation.message.Message;

import java.util.List;

/**
 * AgentExecutor
 *
 * @author Rizwan Idrees
 */
public interface AgentExecutor {

    Agent getAgent();

    List<AssistantMessage> process(String contextId, String user, List<Message> messages);
}
