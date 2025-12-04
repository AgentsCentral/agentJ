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

    /**
     * Finds a single document in the collection that matches the filter.
     *
     * @param collection the collection to find the document in
     * @param filter the filter to match the document
     * @param convertor the convertor to convert the document to a record
     * @return the record if found, or null if not found
     */
    public <T, D extends AgentJDocument> T findOne(MongoCollection<D> collection,
                               Bson filter, Convertor<D, T> convertor) {
        D document = collection.find(filter).first();
        return Optional.ofNullable(document).map(convertor::convert).orElse(null);
    }

    /**
     * Finds all documents in the collection that match the filter.
     *
     * @param collection the collection to find documents in
     * @param filter the filter to match documents
     * @param sort the sort order for the documents
     * @param convertor the convertor to convert documents to records
     * @return a list of records
     */
    public <T, D extends AgentJDocument> List<T> find(MongoCollection<D> collection,
                            Bson filter,
                            Bson sort, Convertor<D, T> convertor) {
        FindIterable<D> iterableDocs = collection.find(filter).sort(sort);
        return iterableDocs.map(convertor::convert).into(new ArrayList<>());
    }

    /**
     * Inserts a record into the collection.
     *
     * @param collection the collection to insert the record into
     * @param record the record to be inserted
     * @param convertor the convertor to convert the record to a document
     */
    public <T, D extends AgentJDocument> void insertOne(MongoCollection<D> collection, T record,
                              Convertor<T, D> convertor) {

        D document = convertor.convert(record);

        Instant now = Instant.now();
        document.setCreatedAt(now);
        document.setUpdatedAt(now);

        collection.insertOne(document);
    }

    /**
     * Inserts a list of records into the collection.
     *
     * @param collection the collection to insert the records into
     * @param records the records to be inserted
     * @param convertor the convertor to convert the records to documents
     */
    public <T, D extends AgentJDocument> void insertMany(MongoCollection<D> collection,
                               List<? extends T> records,
                               Convertor<T, D> convertor) {

        Instant now = Instant.now();

        final List<D> documents = records.stream()
                .map(convertor::convert)
                .peek(document -> {
                    document.setCreatedAt(now);
                    document.setUpdatedAt(now);
                }).toList();

        collection.insertMany(documents);
    }

    /**
     * Upserts a document in the collection.
     *
     * @param collection the collection to upsert the document in
     * @param filter the filter to match the document to be upserted
     * @param record the record to be upserted
     * @param convertor the convertor to convert the record to a document
     * @return the upserted document
     */
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
