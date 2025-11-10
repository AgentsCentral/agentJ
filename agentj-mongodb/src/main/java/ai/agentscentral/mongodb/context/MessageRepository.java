package ai.agentscentral.mongodb.context;

import ai.agentscentral.mongodb.model.MessageDocument;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class MessageRepository {
    public final MongoCollection<MessageDocument> messageCollection;

    public MessageRepository(MongoDatabase database) {
        this.messageCollection = database.getCollection("messages", MessageDocument.class);
    }

    public List<MessageDocument> getMessage(String contextId) {
        final FindIterable<MessageDocument> messages = messageCollection.find(eq("contextId", contextId));
        return messages.into(new ArrayList<>());
    }

    public List<MessageDocument> saveMessage(String contextId, List<MessageDocument> newMessages) {
        boolean isSaved = messageCollection.insertMany(newMessages).wasAcknowledged();
        if (isSaved) {
            long count = messageCollection.countDocuments();
            // Find all documents in the collection
            FindIterable<MessageDocument> messages = messageCollection.find();
            ArrayList<MessageDocument> msgs = messages.into(new ArrayList<>());
            for (MessageDocument message : msgs) {
                System.out.println(message);
            }
            return getMessage(contextId);
        }
        return new ArrayList<>();
    }
}
