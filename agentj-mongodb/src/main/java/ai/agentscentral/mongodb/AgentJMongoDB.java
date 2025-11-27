package ai.agentscentral.mongodb;

import ai.agentscentral.core.convertors.Convertor;
import ai.agentscentral.mongodb.config.MongoDBConfig;
import ai.agentscentral.mongodb.model.MessageDocument;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * MongoDBContextManager
 *
 * @author Mustafa Bhuiyan
 * @author Rizwan Idrees
 */
public class AgentJMongoDB {

    public final MongoDatabase database;

    public AgentJMongoDB(String connectionString, String databaseName) {
        this.database = MongoDBConfig.getMongoDatabase(connectionString, databaseName);
    }

    public <T> MongoCollection<T> getCollection(String collectionName, Class<T> documentClass) {
        return database.getCollection(collectionName, documentClass);
    }

    public <T> T findOne(MongoCollection<MessageDocument> collection,
                               Bson filter,
                               Bson sort, Convertor<MessageDocument, T> convertor) {
        MessageDocument document = collection.find(filter).sort(sort).first();
        return Optional.ofNullable(document).map(convertor::convert).orElse(null);
    }

    public <T> List<T> find(MongoCollection<MessageDocument> collection,
                            Bson filter,
                            Bson sort, Convertor<MessageDocument, T> convertor) {
        FindIterable<MessageDocument> iterableDocs = collection.find(filter).sort(sort);
        return iterableDocs.map(convertor::convert).into(new ArrayList<>());
    }

    public <T> void insertOne(MongoCollection<MessageDocument> collection, T record,
                              Convertor<T, MessageDocument> convertor) {

        Optional.of(record)
                .map(convertor::convert)
                .map(collection::insertOne);
    }

    public <T> void insertMany(MongoCollection<MessageDocument> collection,
                               List<? extends T> records,
                               Convertor<T, MessageDocument> convertor) {


        final List<MessageDocument> documents = records.stream()
                .map(convertor::convert).toList();

        collection.insertMany(documents);
    }

}
