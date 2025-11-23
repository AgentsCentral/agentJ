package ai.agentscentral.mongodb;

import ai.agentscentral.core.convertors.Convertor;
import ai.agentscentral.mongodb.config.MongoDBConfig;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
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

    public MongoCollection<Document> getCollection(String collectionName) {
        return database.getCollection(collectionName);
    }

    public <T> T findOne(MongoCollection<Document> collection,
                               Bson filter,
                               Bson sort, Convertor<Document, T> convertor) {
        Document document = collection.find(filter).sort(sort).first();
        return Optional.ofNullable(document).map(convertor::convert).orElse(null);
    }

    public <T> List<T> find(MongoCollection<Document> collection,
                            Bson filter,
                            Bson sort, Convertor<Document, T> convertor) {
        FindIterable<Document> iterableDocs = collection.find(filter).sort(sort);
        return iterableDocs.map(convertor::convert).into(new ArrayList<>());
    }

    public <T> void insertOne(MongoCollection<Document> collection, T record,
                              Convertor<T, Document> convertor) {

        Optional.of(record)
                .map(convertor::convert)
                .map(collection::insertOne);
    }

    public <T> void insertMany(MongoCollection<Document> collection,
                               List<? extends T> records,
                               Convertor<T, Document> convertor) {


        final List<Document> documents = records.stream()
                .map(convertor::convert).toList();

        collection.insertMany(documents);
    }

}
