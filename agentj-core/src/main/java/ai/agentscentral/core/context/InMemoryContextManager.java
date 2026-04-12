package ai.agentscentral.core.context;

import ai.agentscentral.core.session.message.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.synchronizedList;
import static java.util.Comparator.comparing;

/**
 * In-memory {@link ContextManager} implementation backed by a {@link ConcurrentHashMap}.
 *
 * <p>Each context entry is a {@link java.util.Collections#synchronizedList synchronized}
 * {@link ArrayList} that is created lazily on the first access.  Messages are sorted by
 * {@link ai.agentscentral.core.session.message.Message#timestamp()} on every
 * {@link #getContext} call, ensuring callers always receive a temporally ordered view
 * regardless of insertion order.</p>
 *
 * <p>{@link #addContext} is implemented as a delegating call to {@link #getContext}
 * followed by {@link List#addAll}, so context creation and appending share the same
 * lazy-initialisation path.</p>
 *
 * <p>This implementation is suitable for single-node deployments and testing.  For
 * clustered or durable storage, provide an alternative {@link ContextManager}
 * implementation (e.g. the MongoDB-backed one in {@code agentj-mongodb}).</p>
 *
 * @author Rizwan Idrees
 */
public class InMemoryContextManager implements ContextManager {

    final Map<String, List<Message>> contexts = new ConcurrentHashMap<>();

    /**
     * {@inheritDoc}
     *
     * <p>Creates a new {@link java.util.Collections#synchronizedList synchronized list}
     * for {@code contextId} if none exists, then sorts and returns the list.</p>
     */
    @Override
    public List<Message> getContext(String contextId) {
        if(!contexts.containsKey(contextId)){
            contexts.put(contextId, synchronizedList(new ArrayList<>()));
        }

        final List<Message> context = contexts.get(contextId);
        context.sort(comparing(Message::timestamp));
        return context;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Delegates to {@link #getContext} (creating the context if absent) and then
     * appends all elements of {@code newMessages}.</p>
     */
    @Override
    public void addContext(String contextId, List<? extends Message> newMessages) {
        getContext(contextId).addAll(newMessages);
    }
}
