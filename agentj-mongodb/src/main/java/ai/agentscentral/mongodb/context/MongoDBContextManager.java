package ai.agentscentral.mongodb.context;

import ai.agentscentral.core.context.ContextManager;
import ai.agentscentral.core.session.message.Message;
import ai.agentscentral.mongodb.AgentJMongoDB;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;

import static ai.agentscentral.mongodb.convertors.DocumentToMessageConvertor.documentToMessageConvertor;
import static ai.agentscentral.mongodb.convertors.MessageToDocumentConvertor.messageToDocumentConvertor;

/**
 * MongoDBContextManager
 *
 * @author Mustafa Bhuiyan
 * @author Rizwan Idrees
 */
public class MongoDBContextManager implements ContextManager {

    private final AgentJMongoDB agentJMongoDB;
    private final MongoCollection<Document> contextCollection;

    public MongoDBContextManager(AgentJMongoDB agentJMongoDB, String contextCollection) {
        this.agentJMongoDB = agentJMongoDB;
        this.contextCollection = agentJMongoDB.getCollection(contextCollection);
    }

    @Override
    public List<Message> getContext(String contextId) {
        Bson filter = Filters.eq("contextId", contextId);
        Bson sort = Sorts.ascending();
        return agentJMongoDB.find(contextCollection, filter, sort, documentToMessageConvertor);
    }

    @Override
    public void addContext(String contextId, List<? extends Message> newMessages) {
        agentJMongoDB.insertMany(contextCollection, newMessages, messageToDocumentConvertor);
    }
}
