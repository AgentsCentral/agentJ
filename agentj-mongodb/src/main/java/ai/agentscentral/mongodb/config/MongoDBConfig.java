package ai.agentscentral.mongodb.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.lang3.StringUtils;

/**
 * MongoDBConfig
 *
 * @author Mustafa Bhuiyan
 */
public class MongoDBConfig {

    private static MongoClient mongoClient;
    private static final String DEFAULT_DATABASE_NAME = "agentj_mongodb";

    /**
     * Create or Retrieve the MongoDB database instance.
     *
     * @param connectionString the connection string for the MongoDB database
     * @param databaseName the name of the database to retrieve
     * @return the MongoDB database instance
     */
    public static synchronized MongoDatabase getMongoDatabase(String connectionString, String databaseName) {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(connectionString);
        }

        if (StringUtils.isAllBlank(databaseName)) {
            databaseName = DEFAULT_DATABASE_NAME;
        }

        return mongoClient.getDatabase(databaseName)
                .withCodecRegistry(MongoDBCodecRegistry.AUTO_CODEC_REGISTRY);
    }
}
