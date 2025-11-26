package ai.agentscentral.mongodb.context;

import ai.agentscentral.core.handoff.DefaultHandoffInstruction;
import ai.agentscentral.core.handoff.Handoff;
import ai.agentscentral.core.handoff.HandoffInstruction;
import ai.agentscentral.core.session.message.AssistantMessage;
import ai.agentscentral.core.session.message.DeveloperMessage;
import ai.agentscentral.core.session.message.HandOffMessage;
import ai.agentscentral.core.session.message.InterruptParameterValue;
import ai.agentscentral.core.session.message.InterruptPreCallResult;
import ai.agentscentral.core.session.message.Message;
import ai.agentscentral.core.session.message.MessagePart;
import ai.agentscentral.core.session.message.MessagePartType;
import ai.agentscentral.core.session.message.TextPart;
import ai.agentscentral.core.session.message.ToolInterruptMessage;
import ai.agentscentral.core.session.message.ToolInterruptParameter;
import ai.agentscentral.core.session.message.ToolInterruptPart;
import ai.agentscentral.core.session.message.ToolMessage;
import ai.agentscentral.core.session.message.UserInterruptMessage;
import ai.agentscentral.core.session.message.UserInterruptPart;
import ai.agentscentral.core.session.message.UserMessage;
import ai.agentscentral.core.tool.DefaultToolCallInstruction;
import ai.agentscentral.core.tool.ToolCallInstruction;
import ai.agentscentral.mongodb.AgentJMongoDB;
import ai.agentscentral.mongodb.config.MongoDBTestConfig;
import ai.agentscentral.mongodb.model.MessageDocument;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Map;

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
        MongoDBTestConfig.clearCollection(agentJMongoDB.getCollection(CONTEXT_COLLECTION_NAME, MessageDocument.class));
    }

    @AfterAll
    public static void tearDown() {
        MongoDBTestConfig.dropDatabase();
    }

    @Test
    public void getContext_whenContextDoesNotExist_shouldReturnEmptyList() {
        // Act
        List<Message> result = contextManager.getContext("non-existent-context");

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty(), "Should return empty list for non-existent context");
    }

    @Test
    public void addContext_whenEmptyMessagesList_shouldThrowException() {
        // Arrange
        String contextId = "test-empty-messages";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            contextManager.addContext(contextId, List.of());
        }, "Should throw IllegalArgumentException for empty messages list");
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

    @Test
    public void getContext_whenAddMultipleMessageTypes_shouldReturnAllMessages() {
        // Arrange
        String contextId = "test-context-multiple-types";
        String userMsgContent1 = "User first message";
        String assistantMsgContent1 = "Assistant response";

        // User Message
        Message userMessage = createTestUserMessage(contextId, "msg-001", userMsgContent1);

        // Assistant Message with all fields
        Message assistantMessage = createTestAssistantMessage(
                contextId,
                "msg-002",
                assistantMsgContent1,
                List.of(new DefaultToolCallInstruction(
                        "tool1",
                        "call1",
                        "response1",
                        Map.of("param1", "value1")
                )),
                List.of(new DefaultHandoffInstruction(
                        "callId1",
                        new Handoff("target1", "response1", "HandOff Description")
                ))
        );

        List<Message> testMessages = List.of(userMessage, assistantMessage);

        // Act
        contextManager.addContext(contextId, testMessages);
        List<Message> savedContexts = contextManager.getContext(contextId);

        // Assert
        assertNotNull(savedContexts, "Result should not be null");
        assertEquals(2, savedContexts.size(), "Should return all messages including different types");

        // Verify each message based on their type
        for (Message message: savedContexts) {
            switch (message) {
                case UserMessage um -> {
                    assertEquals(userMsgContent1, ((TextPart) um.parts()[0]).text());
                }
                case AssistantMessage am -> {
                    assertEquals(assistantMsgContent1, ((TextPart) am.parts()[0]).text());
                }
                case null, default -> fail("Unexpected message type: " +
                        (message == null ? "null" : message.getClass().getName()));
            }
        }
    }

    @Test
    public void getContexts_whenAddContextWithToolMessages_shouldReturnContextWithMessages() {
        // Arrange
        String contextId = "test-context-tool-msg";
        String messageId = "tool-msg-001";
        String toolCallId = "tool-call-001";
        String toolName = "test-tool";
        String toolResult = "tool result";
        
        ToolMessage toolMessage = new ToolMessage(
                contextId,
                messageId,
                toolCallId,
                toolName,
                new TextPart[]{new TextPart(MessagePartType.text, toolResult)},
                Instant.now().toEpochMilli()
        );

        // Act
        contextManager.addContext(contextId, List.of(toolMessage));
        List<Message> result = contextManager.getContext(contextId);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(1, result.size(), "Should return the tool message");
        
        Message message = result.getFirst();
        assertInstanceOf(ToolMessage.class, message, "Should be a ToolMessage");
        ToolMessage savedToolMessage = (ToolMessage) message;
        assertEquals(contextId, savedToolMessage.contextId());
        assertEquals(messageId, savedToolMessage.messageId());
        assertEquals(toolCallId, savedToolMessage.toolCallId());
        assertEquals(toolName, savedToolMessage.toolName());
        assertEquals(toolResult, ((TextPart) savedToolMessage.parts()[0]).text());
    }

    @Test
    public void getContexts_whenAddContextWithHandOffMessages_shouldReturnContextWithMessages() {
        // Arrange
        String contextId = "test-context-handoff-msg";
        String messageId = "handoff-msg-001";
        String handoffId = "handoff-001";
        String agentName = "test-agent";
        String handoffDescription = "handoff description";
        
        HandOffMessage handoffMessage = new HandOffMessage(
                contextId,
                messageId,
                handoffId,
                agentName,
                new TextPart[]{new TextPart(MessagePartType.text, handoffDescription)},
                Instant.now().toEpochMilli()
        );

        // Act
        contextManager.addContext(contextId, List.of(handoffMessage));
        List<Message> result = contextManager.getContext(contextId);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(1, result.size(), "Should return the handoff message");
        
        Message message = result.getFirst();
        assertInstanceOf(HandOffMessage.class, message, "Should be a HandoffMessage");
        HandOffMessage savedHandoffMessage = (HandOffMessage) message;
        assertEquals(contextId, savedHandoffMessage.contextId());
        assertEquals(messageId, savedHandoffMessage.messageId());
        assertEquals(handoffId, savedHandoffMessage.handOffId());
        assertEquals(agentName, savedHandoffMessage.agentName());
        assertEquals(handoffDescription, ((TextPart) savedHandoffMessage.parts()[0]).text());
    }

    @Test
    public void getContexts_whenAddContextWithToolInterruptMessages_shouldReturnContextWithMessages() {
        // Arrange
        String contextId = "test-context-tool-interrupt-msg";
        String messageId = "tool-interrupt-msg-001";
        String toolCallId = "tool-call-002";
        String interruptName = "test-interrupt";
        String renderer = "test-renderer";
        Map<String, Object> toolCallParameters = Map.of("param1", "value1");
        List<InterruptPreCallResult> preCallResults = List.of(
            new InterruptPreCallResult("preCall1", "result1")
        );
        List<ToolInterruptParameter> interruptParameters = List.of(
            new ToolInterruptParameter("param1", true)
        );
        
        // Create ToolInterruptPart for the parts array
        ToolInterruptPart toolInterruptPart = new ToolInterruptPart(
            MessagePartType.tool_interrupt,
            toolCallId,
            interruptName,
            renderer,
            toolCallParameters,
            preCallResults,
            interruptParameters
        );
        
        // Create ToolInterruptMessage with the ToolInterruptPart
        ToolInterruptMessage toolInterruptMessage = new ToolInterruptMessage(
            contextId,
            messageId,
            new MessagePart[]{toolInterruptPart},
            Instant.now().toEpochMilli()
        );

        // Act
        contextManager.addContext(contextId, List.of(toolInterruptMessage));
        List<Message> result = contextManager.getContext(contextId);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(1, result.size(), "Should return the tool interrupt message");
        
        Message message = result.getFirst();
        assertInstanceOf(ToolInterruptMessage.class, message, "Should be a ToolInterruptMessage");
        ToolInterruptMessage savedMessage = (ToolInterruptMessage) message;
        
        // Verify the message fields
        assertEquals(contextId, savedMessage.contextId());
        assertEquals(messageId, savedMessage.messageId());
        
        // Verify the ToolInterruptPart in the parts array
        assertEquals(1, savedMessage.parts().length, "Should have one part");
        assertInstanceOf(ToolInterruptPart.class, savedMessage.parts()[0], "Part should be a ToolInterruptPart");
        
        ToolInterruptPart savedPart = (ToolInterruptPart) savedMessage.parts()[0];
        assertEquals(MessagePartType.tool_interrupt, savedPart.type());
        assertEquals(toolCallId, savedPart.toolCallId());
        assertEquals(interruptName, savedPart.interruptName());
        assertEquals(renderer, savedPart.renderer());
        assertEquals(toolCallParameters, savedPart.toolCallParameters());
        assertEquals(preCallResults.size(), savedPart.preCallResults().size());
        assertEquals(interruptParameters.size(), savedPart.interruptParameters().size());
    }

    @Test
    public void getContexts_whenAddContextWithUserInterruptMessages_shouldReturnContextWithMessages() {
        // Arrange
        String contextId = "test-context-user-interrupt-msg";
        String messageId = "user-interrupt-msg-001";
        String toolCallId = "tool-call-003";
        String interruptName = "user-interrupt";
        Map<String, String> interruptParameterValues = Map.of("param1", "value1", "param2", "value2");
        
        // Create UserInterruptPart for the parts array
        UserInterruptPart userInterruptPart = new UserInterruptPart(
            MessagePartType.user_interrupt,
            toolCallId,
            interruptName,
            interruptParameterValues.entrySet().stream()
                .map(e -> new InterruptParameterValue(e.getKey(), e.getValue()))
                .toList()
        );
        
        // Create UserInterruptMessage with the UserInterruptPart
        UserInterruptMessage userInterruptMessage = new UserInterruptMessage(
            contextId,
            messageId,
            interruptParameterValues,
            new MessagePart[]{userInterruptPart},
            Instant.now().toEpochMilli()
        );

        // Act
        contextManager.addContext(contextId, List.of(userInterruptMessage));
        List<Message> result = contextManager.getContext(contextId);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(1, result.size(), "Should return the user interrupt message");
        
        Message message = result.getFirst();
        assertInstanceOf(UserInterruptMessage.class, message, "Should be a UserInterruptMessage");
        UserInterruptMessage savedMessage = (UserInterruptMessage) message;
        
        // Verify the message fields
        assertEquals(contextId, savedMessage.contextId());
        assertEquals(messageId, savedMessage.messageId());
        assertEquals(interruptParameterValues, savedMessage.interruptParameterValues());
        
        // Verify the UserInterruptPart in the parts array
        assertEquals(1, savedMessage.parts().length, "Should have one part");
        assertInstanceOf(UserInterruptPart.class, savedMessage.parts()[0], "Part should be a UserInterruptPart");
        
        UserInterruptPart savedPart = (UserInterruptPart) savedMessage.parts()[0];
        assertEquals(MessagePartType.user_interrupt, savedPart.type());
        assertEquals(toolCallId, savedPart.toolCallId());
        assertEquals(interruptName, savedPart.interruptName());
        
        // Verify interrupt parameters
        assertEquals(interruptParameterValues.size(), savedPart.interruptParameters().size());
        for (Map.Entry<String, String> entry : interruptParameterValues.entrySet()) {
            assertTrue(savedPart.interruptParameters().stream()
                .anyMatch(p -> p.name().equals(entry.getKey()) && p.value().equals(entry.getValue())),
                "Should contain parameter: " + entry.getKey());
        }
    }

    @Test
    public void getContexts_whenAddContextWithDeveloperMessages_shouldReturnContextWithMessages() {
        // Arrange
        String contextId = "test-context-developer-msg";
        String messageId = "dev-msg-001";
        String content = "Developer message content";
        
        DeveloperMessage developerMessage = new DeveloperMessage(
                contextId,
                messageId,
                new TextPart[]{new TextPart(MessagePartType.text, content)},
                Instant.now().toEpochMilli()
        );

        // Act
        contextManager.addContext(contextId, List.of(developerMessage));
        List<Message> result = contextManager.getContext(contextId);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(1, result.size(), "Should return the developer message");
        
        Message message = result.getFirst();
        assertInstanceOf(DeveloperMessage.class, message, "Should be a DeveloperMessage");
        DeveloperMessage savedMessage = (DeveloperMessage) message;

        // Verify the message fields
        assertEquals(contextId, savedMessage.contextId());
        assertEquals(messageId, savedMessage.messageId());
        assertEquals(content, ((TextPart)savedMessage.parts()[0]).text());
    }

    private Message createTestUserMessage(String contextId, String messageId, String content) {
        return new UserMessage(
                contextId,
                messageId,
                new TextPart[]{new TextPart(MessagePartType.text, content)},
                Instant.now().toEpochMilli()
        );
    }

    private Message createTestAssistantMessage(
            String contextId,
            String messageId,
            String content,
            List<ToolCallInstruction> toolCalls,
            List<HandoffInstruction> handoffs
    ) {
        return new AssistantMessage(
                contextId,
                messageId,
                new TextPart[]{new TextPart(MessagePartType.text, content)},
                toolCalls,
                handoffs,
                Instant.now().toEpochMilli()
        );
    }
}