package ai.agentscentral.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.MongoClientSettings;
import com.mongodb.ConnectionString;
import org.junit.jupiter.api.Test;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;

class MongoDBContextManagerTest {

    MongoClient mongoClient = MongoClients.create(
            MongoClientSettings.builder().applyConnectionString(new ConnectionString("mongodb://localhost:27017/"))
                    .applyToSocketSettings(builder ->
                            builder.connectTimeout(5L, SECONDS))
                    .build());
    private AgentJMongoDB mongoDB = new AgentJMongoDB(mongoClient, "aj");

    private MongoDBContextManager contextManager = new MongoDBContextManager(mongoDB, "context");



    @Test
    void should_save_assistantMessage(){




    }

}