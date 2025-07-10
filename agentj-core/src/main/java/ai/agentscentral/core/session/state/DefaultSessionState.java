package ai.agentscentral.core.session.state;

/**
 * DefaultConversationState
 *
 * @param sessionId
 * @param currentAgent
 *
 * @author Rizwan Idrees
 */
public record DefaultSessionState(String sessionId, String currentAgent) implements SessionState {
}
