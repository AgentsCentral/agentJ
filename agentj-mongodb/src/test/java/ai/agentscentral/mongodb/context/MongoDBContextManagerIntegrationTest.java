package ai.agentscentral.mongodb.context;

import ai.agentscentral.core.session.message.Message;
import ai.agentscentral.core.session.message.MessagePartType;
import ai.agentscentral.core.session.message.TextPart;
import ai.agentscentral.core.session.message.UserMessage;
import ai.agentscentral.mongodb.AgentJMongoDB;
import ai.agentscentral.mongodb.config.MongoDBTestConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * MongoDBContextManagerIntegrationTest
 *
 * @author Mustafa Bhuiyan
 */
class MongoDBContextManagerIntegrationTest {
    private static MongoDBContextManager contextManager;
    private static final AgentJMongoDB agentJMongoDB = MongoDBTestConfig.getAgentJMongoDB();
    private static final String CONTEXT_COLLECTION_NAME = "contexts";

    @BeforeAll
    public static void setUp() {
        contextManager = new MongoDBContextManager(agentJMongoDB, CONTEXT_COLLECTION_NAME);
    }

    @BeforeEach
    public void cleanUp() {
        MongoDBTestConfig.clearCollection(agentJMongoDB.getCollection(CONTEXT_COLLECTION_NAME));
    }

    @AfterAll
    public static void tearDown() {
        MongoDBTestConfig.dropDatabase();
    }

    @Test
    public void getContext_whenNoMessages_shouldReturnEmptyList() {
        // Arrange

        // Act
        List<Message> result = contextManager.getContext("test-context");

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void getContexts_whenAddContextWithUserMessages_shouldReturnContextWithMessages() {
        // Arrange
        String contextId = "test-context-001";
        List<Message> testMessages = List.of(
                createTestUserMessage(contextId, "msg-001", "Test message 1"),
                createTestUserMessage(contextId, "msg-002", "Test message 2")
        );

        // Save messages directly using repository
        contextManager.addContext(contextId, testMessages);

        // Act
        List<Message> result = contextManager.getContext(contextId);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(testMessages.size(), result.size(), "Should return all messages for the context");

        // Verify each message
        for (int i = 0; i < testMessages.size(); i++) {
            Message expected = testMessages.get(i);
            Message actual = result.get(i);

            assertEquals(expected.contextId(), actual.contextId());
            assertEquals(expected.messageId(), actual.messageId());
        }
    }

    private Message createTestUserMessage(String contextId, String messageId, String content) {
        return new UserMessage(contextId, messageId,
                new TextPart[]{new TextPart(MessagePartType.text, content)},
                Instant.now().toEpochMilli());
    }

}