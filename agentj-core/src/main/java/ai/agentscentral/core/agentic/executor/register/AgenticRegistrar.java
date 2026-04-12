package ai.agentscentral.core.agentic.executor.register;

import ai.agentscentral.core.agentic.Agentic;
import ai.agentscentral.core.agentic.executor.AgenticExecutor;
import ai.agentscentral.core.team.Team;

import java.util.Optional;

/**
 * Registry that maps {@link Agentic} entities to their {@link AgenticExecutor} instances.
 *
 * <p>Executors are registered at initialization time by
 * {@link ai.agentscentral.core.agentic.executor.AgenticExecutorInitializer} and are later
 * looked up by name during handoff resolution to obtain the target executor.</p>
 *
 * @author Rizwan Idrees
 */
public interface AgenticRegistrar {

    /**
     * Registers an executor for the given agentic entity.
     *
     * @param agentic     the agent or team being registered
     * @param partOfTeam  the parent team this entity belongs to, or {@code null} if top-level
     * @param executor    the executor to associate with this entity
     */
    void register(Agentic agentic, Team partOfTeam, AgenticExecutor<? extends Agentic> executor);

    /**
     * Finds a registered agentic by name within a specific team scope.
     *
     * @param agenticName    name of the agent or team to find
     * @param partOfTeamName name of the parent team to scope the search, or {@code null}
     *                       to search top-level entities
     * @return an {@code Optional} containing the matching {@link RegisteredAgentic}, or empty
     */
    Optional<RegisteredAgentic> find(String agenticName, String partOfTeamName);

    /**
     * Finds a registered agentic by name across all team scopes.
     *
     * <p>Teams are searched before agents. Use this for handoff resolution when the target's
     * parent team is not known.</p>
     *
     * @param agenticName name of the agent or team to find
     * @return an {@code Optional} containing the matching {@link RegisteredAgentic}, or empty
     */
    Optional<RegisteredAgentic> findAny(String agenticName);

}
