package ai.agentscentral.mongodb.config;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * Test configuration for MongoDB integration tests.
 * Manages the MongoDB test container lifecycle and provides a configured MongoClient and Test Database.
 */
@Testcontainers
public class MongoTestConfig {

    @Container
    private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    private static final String TEST_DB = "agentj_test";

    private static MongoDatabase mongoTestDatabase;

    private static final Logger log = LoggerFactory.getLogger(MongoTestConfig.class);

    /**
     * Returns a singleton MongoDatabase instance for testing.
     * Starts the MongoDB container if not already running.
     * 
     * @return Configured MongoDatabase instance for testing
     */
    public static synchronized MongoDatabase getMongoTestDatabase() {
        if (mongoTestDatabase == null) {
            if (!mongoDBContainer.isRunning()) {
                log.info("Starting MongoDB test container...");
                mongoDBContainer.start();
                log.info("MongoDB test container started at: {}", mongoDBContainer.getConnectionString());
            }

            log.debug("Connecting to test database: {}", TEST_DB);
            mongoTestDatabase = MongoConfig.getMongoDatabase(mongoDBContainer.getConnectionString(), TEST_DB);
            log.info("Successfully connected to test database: {}", mongoTestDatabase.getName());
        }
        return mongoTestDatabase;
    }

    /**
     * Clears a specific collection from the test database.
     */
    public static void clearCollection(MongoCollection<?> collection) {
        collection.deleteMany(new Document());
    }

    /**
     * Clears all collection from the test database.
     */
    public static void dropDatabase() {
        getMongoTestDatabase().drop();
    }
}
