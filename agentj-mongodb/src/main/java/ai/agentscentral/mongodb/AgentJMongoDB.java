package ai.agentscentral.mongodb;

import ai.agentscentral.core.convertors.Convertor;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;
import java.util.Optional;

/**
 * MongoDBContextManager
 *
 * @author Mustafa Kamal
 * @author Rizwan Idrees
 */
public class AgentJMongoDB {

    private final MongoClient mongoClient;
    private final MongoDatabase database;


    public AgentJMongoDB(MongoClient mongoClient, String databaseName) {
        this.mongoClient = mongoClient;
        this.database = mongoClient.getDatabase(databaseName);
    }

    public MongoCollection<Document> getCollection(String collectionName) {
        return database.getCollection(collectionName);
    }

    public void find(MongoCollection<Document> collection,
                     List<Bson> filters,
                     List<Bson> sorts){

    }

    public <T> void insert(MongoCollection<Document> collection,
                           T record,
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
