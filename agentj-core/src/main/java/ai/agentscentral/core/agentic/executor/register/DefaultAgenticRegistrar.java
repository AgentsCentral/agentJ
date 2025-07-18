package ai.agentscentral.core.agentic.executor.register;

import ai.agentscentral.core.agent.Agent;
import ai.agentscentral.core.agentic.Agentic;
import ai.agentscentral.core.agentic.executor.AgenticExecutor;
import ai.agentscentral.core.team.Team;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.EMPTY;

public class DefaultAgenticRegistrar implements AgenticRegistrar {

    private final Map<RegistrationKey, RegisteredAgentic> teamRegistry = new HashMap<>();
    private final Map<RegistrationKey, RegisteredAgentic> agentRegistry = new HashMap<>();

    @Override
    public void register(@Nonnull Agentic agentic, @Nullable Team partOfTeam, AgenticExecutor<? extends Agentic> executor) {
        switch (agentic) {
            case Team team -> registerTeam(team, partOfTeam, executor);
            case Agent agent -> registerAgent(agent, partOfTeam, executor);
            default -> throw new UnsupportedOperationException("Unexpected value: " + agentic);
        }
    }

    @Override
    public Optional<RegisteredAgentic> findAny(String agenticName) {
        return findAnyTeam(agenticName).or(() -> findAnyAgent(agenticName));
    }

    @Override
    public Optional<RegisteredAgentic> find(String agenticName, String partOfTeamName) {
        final RegistrationKey key = new RegistrationKey(agenticName, ofNullable(partOfTeamName).orElse(EMPTY));
        return agentRegistry.containsKey(key) ?
                Optional.of(agentRegistry.get(key)) : Optional.ofNullable(teamRegistry.get(key));
    }

    private void registerTeam(@Nonnull Team team, @Nullable Team partOfTeam, AgenticExecutor<? extends Agentic> executor) {
        final String partOfTeamName = ofNullable(partOfTeam).map(Team::name).orElse(EMPTY);
        final RegistrationKey key = new RegistrationKey(team.name(), partOfTeamName);
        final RegisteredTeam registeredTeam = new RegisteredTeam(team, partOfTeam, executor);
        teamRegistry.putIfAbsent(key, registeredTeam);

    }

    private void registerAgent(@Nonnull Agent agent, @Nullable Team partOfTeam, AgenticExecutor<? extends Agentic> executor) {
        final String partOfTeamName = ofNullable(partOfTeam).map(Team::name).orElse(EMPTY);
        final RegistrationKey key = new RegistrationKey(agent.name(), partOfTeamName);
        final RegisteredAgent registeredAgent = new RegisteredAgent(agent, partOfTeam, executor);
        agentRegistry.putIfAbsent(key, registeredAgent);
    }

    private Optional<RegisteredAgentic> findAnyTeam(String agenticName) {
        return teamRegistry.values().stream().filter(r -> r.executor().getAgentic().name().equals(agenticName))
                .findAny();
    }

    private Optional<RegisteredAgentic> findAnyAgent(String agenticName) {
        return agentRegistry.values().stream().filter(r -> r.executor().getAgentic().name()
                .equals(agenticName)).findAny();
    }
}
