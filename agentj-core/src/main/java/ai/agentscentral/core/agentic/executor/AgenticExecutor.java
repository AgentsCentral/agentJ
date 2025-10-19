package ai.agentscentral.core.agentic.executor;

import ai.agentscentral.core.agentic.Agentic;
import ai.agentscentral.core.session.message.Message;
import ai.agentscentral.core.session.message.UserMessage;
import ai.agentscentral.core.session.user.User;

import java.util.List;

/**
 * AgenticExecutor
 *
 * @author Rizwan Idrees
 */
public interface AgenticExecutor<T extends Agentic> {

    T getAgentic();

    List<Message> execute(String contextId,
                          User user,
                          UserMessage userMessage,
                          List<Message> previousContext,
                          List<Message> newMessages,
                          String currentAgenticName,
                          MessageExecutionContext executionContext);
}
