package ai.agentscentral.core.handoff;

import ai.agentscentral.core.conversation.message.Message;

import java.util.List;

/**
 * HandoffFunction
 *
 * @author Rizwan Idrees
 */
@FunctionalInterface
public interface HandoffFunction {

    List<Message> apply(List<Message> messages);

}
