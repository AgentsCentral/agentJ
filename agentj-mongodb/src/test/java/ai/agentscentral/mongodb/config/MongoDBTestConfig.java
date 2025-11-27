package ai.agentscentral.mongodb.config;

import ai.agentscentral.mongodb.AgentJMongoDB;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mongodb.MongoDBContainer;

/**
 * MongoDBTestConfig - Test configuration for MongoDB integration tests.
 * Manages the MongoDB test container lifecycle and provides a configured MongoClient and Test Database.
 *
 * @author Mustafa Bhuiyan
 */
@Testcontainers
public class MongoDBTestConfig {
    @Container
    private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");
    private static final String TEST_DB = "agentj_test_db";
    private static AgentJMongoDB agentJMongoDB;
    private static final Logger logger = LoggerFactory.getLogger(MongoDBTestConfig.class);

    public static AgentJMongoDB getAgentJMongoDB() {
        if (agentJMongoDB == null) {
            if (!mongoDBContainer.isRunning()) {
                mongoDBContainer.start();
                logger.info("MongoDB Test Container is started at: {}", mongoDBContainer.getConnectionString());
            }
            agentJMongoDB = new AgentJMongoDB(mongoDBContainer.getConnectionString(), TEST_DB);
            logger.info("Successfully connected to the test database: {}", agentJMongoDB.database.getName());
        }
        return agentJMongoDB;
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
        getAgentJMongoDB().database.drop();
    }
}
