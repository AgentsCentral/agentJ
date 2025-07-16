package ai.agentscentral.core.context;

/**
 * DefaultContextState
 *
 * @param contextId
 * @param currentTeam
 * @param currentAgent
 * @author Rizwan Idrees
 */
public record DefaultContextState(String contextId,
                                  String currentTeam,
                                  String currentAgent) implements ContextState {
}
