package ai.agentscentral.core.context;

import ai.agentscentral.core.session.message.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.synchronizedList;
import static java.util.Comparator.comparing;

/**
 * InMemoryContextManager
 *
 * @author Rizwan Idrees
 */
public class InMemoryContextManager implements ContextManager {

    final Map<String, List<Message>> contexts = new ConcurrentHashMap<>();

    @Override
    public List<Message> getContext(String contextId) {
        if(!contexts.containsKey(contextId)){
            contexts.put(contextId, synchronizedList(new ArrayList<>()));
        }

        final List<Message> context = contexts.get(contextId);
        context.sort(comparing(Message::timestamp));
        return context;
    }

    @Override
    public void addContext(String contextId, List<? extends Message> newMessages) {
        getContext(contextId).addAll(newMessages);
    }
}
