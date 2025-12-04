package ai.agentscentral.mongodb.context;

import ai.agentscentral.core.context.ContextState;
import ai.agentscentral.core.context.DefaultContextState;
import ai.agentscentral.mongodb.AgentJMongoDB;
import ai.agentscentral.mongodb.config.MongoDBTestConfig;
import ai.agentscentral.mongodb.contextstate.MongoDBContextStateManager;
import ai.agentscentral.mongodb.model.ContextStateDocument;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * MongoDBContextStateManagerIntegrationTest
 *
 * @author Mustafa Bhuiyan
 */
public class MongoDBContextStateManagerIntegrationTest {

    private static MongoDBContextStateManager contextStateManager;
    private static final AgentJMongoDB agentJMongoDB = MongoDBTestConfig.getAgentJMongoDB();
    private static final String CONTEXT_STATE_COLLECTION_NAME = "context_states";
    private static final String TEST_CONTEXT_ID = "test-context-1";
    private static final String TEST_TEAM = "test-team";
    private static final String TEST_AGENT = "test-agent";
    private static final String TEST_PART_OF_TEAM = "test-part-of-team";

    @BeforeAll
    public static void setUp() {
        contextStateManager = new MongoDBContextStateManager(agentJMongoDB, CONTEXT_STATE_COLLECTION_NAME);
    }

    @BeforeEach
    public void cleanUp() {
        MongoDBTestConfig.clearCollection(
                agentJMongoDB.getCollection(CONTEXT_STATE_COLLECTION_NAME, ContextStateDocument.class));
    }

    @Test
    public void test_getCurrentState_whenContextDoesNotExist_shouldReturnEmptyOptional() {
        // Act
        Optional<ContextState> result = contextStateManager.getCurrentState("non-existent-id");
        
        // Assert
        assertTrue(result.isEmpty(), "Should return empty optional for non-existent context ID");
    }

    @Test
    public void test_getCurrentState_whenContextIdIsNull_shouldReturnEmptyOptional() {
        // Act
        Optional<ContextState> result = contextStateManager.getCurrentState(null);

        // Assert
        assertTrue(result.isEmpty(), "Should return empty optional for null context ID");
    }

    @Test
    public void test_updateStateAndGetCurrentState_whenContextStateIsSaved_shouldReturnSavedState() {
        // Arrange
        ContextState originalState = new DefaultContextState(
                TEST_CONTEXT_ID,
                TEST_TEAM,
                TEST_AGENT,
                TEST_PART_OF_TEAM
        );

        // Act - Save the state
        ContextState savedState = contextStateManager.updateState(originalState);
        
        // Assert - Check if the saved state matches the original
        assertNotNull(savedState, "Saved state should not be null");
        assertEquals(TEST_CONTEXT_ID, savedState.contextId());
        assertEquals(TEST_TEAM, savedState.currentTeam());
        assertEquals(TEST_AGENT, savedState.currentAgent());
        assertEquals(TEST_PART_OF_TEAM, savedState.partOfTeam());

        // Act - Retrieve the state
        Optional<ContextState> retrievedStateOpt = contextStateManager.getCurrentState(TEST_CONTEXT_ID);
        
        // Assert - Check if the retrieved state matches the saved state
        assertTrue(retrievedStateOpt.isPresent(), "State should be present");
        ContextState retrievedState = retrievedStateOpt.get();
        assertEquals(savedState.contextId(), retrievedState.contextId());
        assertEquals(savedState.currentTeam(), retrievedState.currentTeam());
        assertEquals(savedState.currentAgent(), retrievedState.currentAgent());
        assertEquals(savedState.partOfTeam(), retrievedState.partOfTeam());
    }

    @Test
    public void test_updateState_whenContextStateExists_shouldUpdateExistingState() {
        // Arrange - Create and save initial state
        ContextState initialState = new DefaultContextState(
                TEST_CONTEXT_ID,
                "initial-team",
                "initial-agent",
                "initial-part-of-team"
        );
        contextStateManager.updateState(initialState);

        // Create updated state with same context ID but different values
        ContextState updatedState = new DefaultContextState(
                TEST_CONTEXT_ID,
                "updated-team",
                "updated-agent",
                "updated-part-of-team"
        );

        // Act - Update the state
        ContextState result = contextStateManager.updateState(updatedState);

        // Assert - Check if the updated state was saved correctly
        assertNotNull(result, "Updated state should not be null");
        assertEquals(TEST_CONTEXT_ID, result.contextId());
        assertEquals("updated-team", result.currentTeam());
        assertEquals("updated-agent", result.currentAgent());
        assertEquals("updated-part-of-team", result.partOfTeam());

        // Verify the state was updated in the database
        Optional<ContextState> retrievedState = contextStateManager.getCurrentState(TEST_CONTEXT_ID);
        assertTrue(retrievedState.isPresent(), "State should be present after update");
        assertEquals("updated-team", retrievedState.get().currentTeam());
    }

    @Test
    public void test_updateState_whenStateIsNull_shouldThrowIllegalArgumentException() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> contextStateManager.updateState(null),
                "Should throw IllegalArgumentException when state is null"
        );
        
        assertEquals("New state cannot be null", exception.getMessage());
    }
}
