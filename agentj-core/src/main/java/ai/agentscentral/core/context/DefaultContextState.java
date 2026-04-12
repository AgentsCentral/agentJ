package ai.agentscentral.core.context;

import jakarta.annotation.Nonnull;

/**
 * Default immutable implementation of {@link ContextState}.
 *
 * <p>Created by the executor infrastructure after each handoff to record the newly active
 * agent and team, and persisted via
 * {@link ContextStateManager#updateState(ContextState)}.  The record is then read back
 * on the next conversational turn to route the message to the correct executor.</p>
 *
 * @param contextId    the unique identifier of the conversation context; must not be
 *                     {@code null}
 * @param currentTeam  the name of the team currently holding execution, or {@code null}
 *                     for a standalone agent
 * @param currentAgent the name of the agent currently executing within the team or
 *                     standalone; may be {@code null} before the first handoff resolves
 *                     the target
 * @param partOfTeam   the name of the parent team the current agent belongs to, used for
 *                     nested-team routing; may be {@code null}
 *
 * @author Rizwan Idrees
 */
public record DefaultContextState(@Nonnull String contextId,
                                  String currentTeam,
                                  String currentAgent,
                                  String partOfTeam) implements ContextState {
}
