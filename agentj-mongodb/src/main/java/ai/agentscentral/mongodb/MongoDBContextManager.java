package ai.agentscentral.mongodb;

import ai.agentscentral.core.context.ContextManager;
import ai.agentscentral.core.session.message.Message;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;

import static ai.agentscentral.mongodb.convertors.MessageToDocumentConvertor.messageToDocumentConvertor;

/**
 * MongoDBContextManager
 *
 * @author Mustafa Kamal
 * @author Rizwan Idrees
 */
public class MongoDBContextManager implements ContextManager {


    private final AgentJMongoDB mongoDB;
    private final MongoCollection<Document> contextCollection;

    public MongoDBContextManager(AgentJMongoDB mongoDB, String contextCollection) {
        this.mongoDB = mongoDB;
        this.contextCollection = mongoDB.getCollection(contextCollection);
    }


    @Override
    public List<Message> getContext(String contextId) {
        Bson filters = Filters.eq("contextId", contextId);
        contextCollection.find(filters).sort(Sorts.descending());
        return null;
    }

    @Override
    public void addContext(String contextId, List<? extends Message> newMessages) {
        mongoDB.insertMany(contextCollection, newMessages, messageToDocumentConvertor);
    }


}
