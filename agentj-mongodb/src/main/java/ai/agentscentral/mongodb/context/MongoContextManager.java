package ai.agentscentral.mongodb.context;

import ai.agentscentral.core.context.ContextManager;
import ai.agentscentral.core.session.message.Message;
import ai.agentscentral.mongodb.codec.MessageConverter;
import ai.agentscentral.mongodb.model.MessageDocument;

import java.util.ArrayList;
import java.util.List;

public class MongoContextManager implements ContextManager {

    private final MessageRepository messageRepository;

    public MongoContextManager(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Message> getContext(String contextId) {
        List<MessageDocument> messageDocs = messageRepository.getMessage(contextId);
        return toMessages(messageDocs);
    }

    @Override
    public List<Message> addContext(String contextId, List<? extends Message> newMessages) {
        List<MessageDocument> messageDocuments = toMessageDocuments(newMessages);
        List<MessageDocument> allMessageDocs = messageRepository.saveMessage(contextId, messageDocuments);
        return toMessages(allMessageDocs);
    }

    private List<MessageDocument> toMessageDocuments(List<? extends Message> newMessages) {
        List<MessageDocument> messageDocuments = new ArrayList<>();
        for (Message message : newMessages) {
            messageDocuments.add(MessageConverter.toMessageDocument(message));
        }
        return messageDocuments;
    }

    private List<Message> toMessages(List<MessageDocument> messageDocs) {
        List<Message> messages = new ArrayList<>();
        for (MessageDocument messageDoc : messageDocs) {
            messages.add(MessageConverter.toMessage(messageDoc));
        }
        return messages;
    }
}
