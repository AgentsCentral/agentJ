package ai.agentscentral.mongodb.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoConfig {
    private static final Logger log = LoggerFactory.getLogger(MongoConfig.class);
    private static MongoClient mongoClient;

    public static synchronized MongoDatabase getMongoDatabase(String connectionString, String databaseName) {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(connectionString);
        }
        
        return mongoClient.getDatabase(databaseName)
                .withCodecRegistry(MongoCodecRegistry.AUTO_CODEC_REGISTRY);
    }
}
