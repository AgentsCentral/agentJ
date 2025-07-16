package ai.agentscentral.core.agentic.executor.register;

import ai.agentscentral.core.agentic.Agentic;
import ai.agentscentral.core.agentic.executor.AgenticExecutor;
import ai.agentscentral.core.team.Team;

import java.util.Optional;

public interface AgenticRegistrar {

    void register(Agentic agentic, Team partOfTeam, AgenticExecutor<? extends Agentic> executor);

    Optional<Registered> find(String agenticName, String partOfTeamName);

    Optional<Registered> findAny(String agenticName);

}
