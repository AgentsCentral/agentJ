package ai.agentscentral.mongodb.context;

import ai.agentscentral.core.context.ContextManager;
import ai.agentscentral.core.session.message.Message;
import ai.agentscentral.mongodb.AgentJMongoDB;
import ai.agentscentral.mongodb.model.MessageDocument;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.conversions.Bson;

import java.util.List;

import static ai.agentscentral.mongodb.convertors.MessageConverter.toMessageConverter;
import static ai.agentscentral.mongodb.convertors.MessageConverter.toMessageDocumentConverter;

/**
 * MongoDBContextManager
 *
 * @author Mustafa Bhuiyan
 * @author Rizwan Idrees
 */
public class MongoDBContextManager implements ContextManager {

    private final AgentJMongoDB agentJMongoDB;
    private final MongoCollection<MessageDocument> contextCollection;

    public MongoDBContextManager(AgentJMongoDB agentJMongoDB, String contextCollectionName) {
        this.agentJMongoDB = agentJMongoDB;
        this.contextCollection = agentJMongoDB.getCollection(contextCollectionName, MessageDocument.class);
    }

    /**
     * Retrieves the context message list for the specified context ID.
     *
     * @param contextId the unique identifier of the context message list
     * @return a List of Messages if found, or empty if not found
     */
    @Override
    public List<Message> getContext(String contextId) {
        Bson filter = Filters.eq("contextId", contextId);
        Bson sort = Sorts.ascending();
        return agentJMongoDB.find(contextCollection, filter, sort, toMessageConverter);
    }

    /**
     * Adds a list of messages to the context for the specified context ID.
     *
     * @param contextId the unique identifier of the context message list
     * @param newMessages the list of messages to be added
     * @throws IllegalArgumentException if the messages list is null or empty
     */
    @Override
    public void addContext(String contextId, List<? extends Message> newMessages) {
        if (newMessages == null || newMessages.isEmpty()) {
            throw new IllegalArgumentException("Messages list cannot be null or empty");
        }
        agentJMongoDB.insertMany(contextCollection, newMessages, toMessageDocumentConverter);
    }
}
