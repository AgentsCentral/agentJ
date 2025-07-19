package ai.agentscentral.core.agentic.executor.register;

import ai.agentscentral.core.agentic.Agentic;
import ai.agentscentral.core.agentic.executor.AgenticExecutor;
import ai.agentscentral.core.team.Team;

import java.util.Optional;

/**
 * AgenticRegistrar
 *
 * @author Rizwan Idrees
 */
public interface AgenticRegistrar {

    void register(Agentic agentic, Team partOfTeam, AgenticExecutor<? extends Agentic> executor);

    Optional<RegisteredAgentic> find(String agenticName, String partOfTeamName);

    Optional<RegisteredAgentic> findAny(String agenticName);

}
