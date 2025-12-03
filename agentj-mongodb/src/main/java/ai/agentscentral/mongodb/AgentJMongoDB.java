package ai.agentscentral.mongodb;

import ai.agentscentral.core.convertors.Convertor;
import ai.agentscentral.mongodb.config.MongoDBConfig;
import ai.agentscentral.mongodb.model.AgentJDocument;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;
import org.bson.conversions.Bson;

import java.time.Instant;
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

    public <T, D> T findOne(MongoCollection<D> collection,
                               Bson filter, Convertor<D, T> convertor) {
        D document = collection.find(filter).first();
        return Optional.ofNullable(document).map(convertor::convert).orElse(null);
    }

    public <T, D> List<T> find(MongoCollection<D> collection,
                            Bson filter,
                            Bson sort, Convertor<D, T> convertor) {
        FindIterable<D> iterableDocs = collection.find(filter).sort(sort);
        return iterableDocs.map(convertor::convert).into(new ArrayList<>());
    }

    public <T, D> void insertOne(MongoCollection<D> collection, T record,
                              Convertor<T, D> convertor) {

        Optional.of(record)
                .map(convertor::convert)
                .map(collection::insertOne);
    }

    public <T, D> void insertMany(MongoCollection<D> collection,
                               List<? extends T> records,
                               Convertor<T, D> convertor) {


        final List<D> documents = records.stream()
                .map(convertor::convert).toList();

        collection.insertMany(documents);
    }

    public <T, D extends AgentJDocument> D upsert(MongoCollection<D> collection, Bson filter, T record,
                                                     Convertor<T, D> convertor) {
        Instant now = Instant.now();

        D document = convertor.convert(record);
        document.setUpdatedAt(now);

        boolean exists = collection.countDocuments(filter) > 0;
        if (!exists) {
            document.setCreatedAt(now);
        }

        return collection.findOneAndReplace(filter, document,
                new FindOneAndReplaceOptions().upsert(true).returnDocument(ReturnDocument.AFTER));
    }
}
