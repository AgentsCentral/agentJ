package ai.agentscentral.core.conversation.state;

/**
 * DefaultConversationState
 *
 * @param conversationId
 * @param currentAgent
 *
 * @author Rizwan Idrees
 */
public record DefaultConversationState(String conversationId, String currentAgent) implements ConversationState {
}
