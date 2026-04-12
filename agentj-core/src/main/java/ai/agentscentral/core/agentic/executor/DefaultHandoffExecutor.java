package ai.agentscentral.core.agentic.executor;

import ai.agentscentral.core.agent.Agent;
import ai.agentscentral.core.agentic.executor.register.AgenticRegistrar;
import ai.agentscentral.core.agentic.executor.register.RegisteredAgent;
import ai.agentscentral.core.agentic.executor.register.RegisteredAgentic;
import ai.agentscentral.core.agentic.executor.register.RegisteredTeam;
import ai.agentscentral.core.context.ContextState;
import ai.agentscentral.core.context.ContextStateManager;
import ai.agentscentral.core.context.DefaultContextState;
import ai.agentscentral.core.handoff.Handoff;
import ai.agentscentral.core.handoff.HandoffInstruction;
import ai.agentscentral.core.team.Team;
import jakarta.annotation.Nonnull;

import java.util.Optional;

/**
 * Default implementation of {@link HandoffExecutor}.
 *
 * <p>Resolves the target agentic by name using an {@link AgenticRegistrar}, then updates
 * the conversation's context state via a {@link ContextStateManager} to reflect the new
 * active agent or team. The updated state and the target's executor are returned as a
 * {@link HandedOff} so the caller can continue the turn under the new entity.</p>
 *
 * @author Rizwan Idrees
 */
public class DefaultHandoffExecutor implements HandoffExecutor {

    private final AgenticRegistrar agenticRegistrar;
    private final ContextStateManager stateManager;


    /**
     * Creates a new {@code DefaultHandoffExecutor}.
     *
     * @param agenticRegistrar registry used to look up the target agent or team by name
     * @param stateManager     manager used to persist the updated context state after a handoff
     */
    public DefaultHandoffExecutor(AgenticRegistrar agenticRegistrar, ContextStateManager stateManager) {
        this.agenticRegistrar = agenticRegistrar;
        this.stateManager = stateManager;
    }

    @Override
    public HandedOff handoff(@Nonnull String contextId,
                             @Nonnull HandoffInstruction instruction) {
        final String agentic = Optional.of(instruction).map(HandoffInstruction::handoff).map(Handoff::agenticName)
                .orElseThrow(() -> new UnsupportedOperationException("Cannot transfer undefined agent"));

        final RegisteredAgentic registeredAgentic = agenticRegistrar.findAny(agentic)
                .orElseThrow(() -> new UnsupportedOperationException("Cannot transfer to un-registered agent"));

        final ContextState contextState = updateState(contextId, registeredAgentic);


        return new HandedOff(contextState.currentAgent(), registeredAgentic.executor());
    }


    private ContextState updateState(String contextId, RegisteredAgentic registeredAgentic) {
        return switch (registeredAgentic) {
            case RegisteredAgent registeredAgent -> updateAgentState(contextId, registeredAgent);
            case RegisteredTeam registeredTeam -> updateTeamState(contextId, registeredTeam);
            default -> throw new UnsupportedOperationException("Cannot update state for agentic "
                    + registeredAgentic.getClass());
        };
    }

    private ContextState updateAgentState(String contextId, RegisteredAgent registeredAgent) {
        final String teamName = Optional.ofNullable(registeredAgent.partOf())
                .map(Team::name).orElse(null);
        final String agentName = Optional.of(registeredAgent.agent()).map(Agent::name)
                .orElse(null);

        return stateManager.updateState(new DefaultContextState(contextId,
                teamName, agentName, teamName));
    }

    private ContextState updateTeamState(String contextId, RegisteredTeam registeredTeam) {
        final String teamName = Optional.ofNullable(registeredTeam)
                .map(RegisteredTeam::team).map(Team::name).orElse(null);

        final String partOfTeamName = Optional.ofNullable(registeredTeam)
                .map(RegisteredTeam::partOf).map(Team::name).orElse(null);

        final String agentName = Optional.ofNullable(registeredTeam)
                .map(RegisteredTeam::team)
                .map(Team::leader)
                .map(Agent::name)
                .orElse(null);

        return stateManager
                .updateState(new DefaultContextState(contextId, teamName, agentName, partOfTeamName));
    }

}
