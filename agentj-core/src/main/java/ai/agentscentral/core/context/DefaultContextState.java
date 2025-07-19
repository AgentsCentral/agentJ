package ai.agentscentral.core.context;

import jakarta.annotation.Nonnull;

/**
 * DefaultContextState
 *
 * @param contextId
 * @param currentTeam
 * @param currentAgent
 * @param partOfTeam
 * @author Rizwan Idrees
 */
public record DefaultContextState(@Nonnull String contextId,
                                  String currentTeam,
                                  String currentAgent,
                                  String partOfTeam) implements ContextState {
}
