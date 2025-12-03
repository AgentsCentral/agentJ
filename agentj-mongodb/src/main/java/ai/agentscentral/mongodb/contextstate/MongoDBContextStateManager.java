package ai.agentscentral.mongodb.contextstate;

import ai.agentscentral.core.context.ContextState;
import ai.agentscentral.core.context.ContextStateManager;
import ai.agentscentral.mongodb.AgentJMongoDB;
import ai.agentscentral.mongodb.model.ContextStateDocument;
import com.mongodb.client.MongoCollection;
import org.bson.conversions.Bson;

import java.util.Optional;

import static ai.agentscentral.mongodb.convertors.ContextStateConverter.contextStateToDocConverter;
import static ai.agentscentral.mongodb.convertors.ContextStateConverter.docToContextStateConverter;
import static com.mongodb.client.model.Filters.eq;

/**
 * MongoDBContextStateManager
 *
 * @author Mustafa Bhuiyan
 * @author Rizwan Idrees
 */
public class MongoDBContextStateManager implements ContextStateManager {

    private final AgentJMongoDB agentJMongoDB;
    private final MongoCollection<ContextStateDocument> contextStateCollection;

    public MongoDBContextStateManager(AgentJMongoDB agentJMongoDB, String contextStateCollectionName) {
        this.agentJMongoDB = agentJMongoDB;
        this.contextStateCollection = agentJMongoDB.getCollection(contextStateCollectionName, ContextStateDocument.class);
    }

    @Override
    public Optional<ContextState> getCurrentState(String contextId) {
        Bson filter = eq("contextId", contextId);
        ContextState contextState = agentJMongoDB.findOne(contextStateCollection, filter, docToContextStateConverter);
        return Optional.ofNullable(contextState);
    }

    @Override
    public ContextState updateState(ContextState newState) {
        if (newState == null) {
            throw new IllegalArgumentException("New state cannot be null");
        }

        Bson filter = eq("contextId", newState.contextId());

        ContextStateDocument contextStateDocument = agentJMongoDB.upsert(contextStateCollection, filter, newState,
                contextStateToDocConverter);
        return docToContextStateConverter.convert(contextStateDocument);
    }
}
