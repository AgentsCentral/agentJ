package ai.agentscentral.core.conversation.context;

import ai.agentscentral.core.conversation.message.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.synchronizedList;
import static java.util.Comparator.comparing;

/**
 * InMemoryConversationContextManager
 *
 *
 * @author Rizwan Idrees
 */
public class InMemoryConversationContextManager implements ConversationContextManager {

    final Map<String, List<Message>> conversations = new ConcurrentHashMap<>();

    @Override
    public List<Message> getContext(String contextId) {
        final List<Message> context = conversations.getOrDefault(contextId, synchronizedList(new ArrayList<>()));
        context.sort(comparing(Message::timestamp));
        return context;
    }

    @Override
    public List<Message> addContext(String contextId, List<? extends Message> newMessages) {
        getContext(contextId).addAll(newMessages);
        return getContext(contextId);
    }
}
