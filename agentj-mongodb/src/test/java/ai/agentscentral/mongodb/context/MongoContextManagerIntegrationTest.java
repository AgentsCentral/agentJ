package ai.agentscentral.mongodb.context;

import ai.agentscentral.core.session.message.Message;
import ai.agentscentral.core.session.message.MessagePart;
import ai.agentscentral.core.session.message.MessagePartType;
import ai.agentscentral.core.session.message.TextPart;
import ai.agentscentral.core.session.message.UserMessage;
import static org.junit.jupiter.api.Assertions.*;

import ai.agentscentral.mongodb.codec.MessageConverter;
import ai.agentscentral.mongodb.config.MongoTestConfig;
import ai.agentscentral.mongodb.model.MessageDocument;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MongoContextManagerIntegrationTest {
    private static MongoContextManager mongoContextManager;
    private static MessageRepository messageRepository;

    @BeforeAll
    public static void setUp() {
        messageRepository = new MessageRepository(MongoTestConfig.getMongoTestDatabase());
        mongoContextManager = new MongoContextManager(messageRepository);
    }

    @BeforeEach
    public void cleanUp() {
        MongoTestConfig.clearCollection(messageRepository.messageCollection);
    }

    @AfterAll
    public static void tearDown() {
        MongoTestConfig.dropDatabase();
    }

    @Test
    public void getContext_whenNoMessages_shouldReturnEmptyList() {
        // Arrange

        // Act
        List<Message> result = mongoContextManager.getContext("test-context");

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void getContext_whenMessagesExist_shouldReturnMessages() {
        // Arrange
        String contextId = "test-context-001";
        List<MessageDocument> testMessageDocs = List.of(
                createTestUserMessageDoc(contextId, "msg-001", "Test message 1"),
                createTestUserMessageDoc(contextId, "msg-002", "Test message 2")
        );
        
        // Save messages directly using repository
        List<MessageDocument> saveMessageDocs = messageRepository.saveMessage(contextId, testMessageDocs);
        
        // Act
        List<Message> result = mongoContextManager.getContext(contextId);
        
        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(testMessageDocs.size(), result.size(), "Should return all messages for the context");
        
        // Verify each message
        for (int i = 0; i < testMessageDocs.size(); i++) {
            MessageDocument expected = testMessageDocs.get(i);
            Message actual = result.get(i);
            
            assertEquals(expected.getContextId(), actual.contextId());
            assertEquals(expected.getMessageId(), actual.messageId());
        }
    }

    private MessageDocument createTestUserMessageDoc(String contextId, String messageId, String content) {
        UserMessage userMessage = new UserMessage(contextId, messageId,
                new TextPart[]{new TextPart(MessagePartType.text, content)},
                Instant.now().toEpochMilli());
        return MessageConverter.toMessageDocument(userMessage);
    }
}
