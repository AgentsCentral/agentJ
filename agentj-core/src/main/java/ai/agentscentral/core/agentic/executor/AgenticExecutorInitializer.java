package ai.agentscentral.core.agentic.executor;

import ai.agentscentral.core.agent.Agent;
import ai.agentscentral.core.agentic.Agentic;
import ai.agentscentral.core.agentic.executor.register.AgenticRegistrar;
import ai.agentscentral.core.context.ContextManager;
import ai.agentscentral.core.context.ContextStateManager;
import ai.agentscentral.core.agentic.AgenticModule;
import ai.agentscentral.core.team.Team;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * Factory responsible for creating and registering {@link AgenticExecutor} instances.
 *
 * <p>Given an {@link Agentic} entity (an {@link ai.agentscentral.core.agent.Agent} or a
 * {@link ai.agentscentral.core.team.Team}), {@link #initialize} selects the appropriate
 * executor implementation, wires its dependencies, registers it with the
 * {@link AgenticRegistrar}, and returns the ready-to-use executor.</p>
 *
 * <p>For teams, the executor type is determined by the team's
 * {@link ai.agentscentral.core.team.TeamMode}; currently only {@code route} mode is
 * supported, which produces a {@link RoutingModeTeamExecutor}.</p>
 *
 * @author Rizwan Idrees
 */
public class AgenticExecutorInitializer {

    private final AgenticModule agenticModule;
    private final AgenticRegistrar registrar;

    /**
     * Creates a new {@code AgenticExecutorInitializer}.
     *
     * @param agenticModule shared module providing tool, handoff, session, and context helpers
     * @param registrar     registry where newly created executors are recorded for later lookup
     */
    public AgenticExecutorInitializer(AgenticModule agenticModule,
                                      AgenticRegistrar registrar) {
        this.agenticModule = agenticModule;
        this.registrar = registrar;
    }

    /**
     * Creates, wires, registers, and returns the appropriate {@link AgenticExecutor} for
     * the given {@link Agentic} entity.
     *
     * @param agentic         the agent or team to initialize
     * @param partOf          the parent team this entity belongs to, or {@code null} if top-level
     * @param stateManager    used by team executors to track the active agent in the conversation
     * @param contextManager  used by agent executors to persist messages
     * @param handoffExecutor executor used when an agent hands off to another entity
     * @return the initialized and registered executor
     * @throws UnsupportedOperationException if {@code agentic} is an unrecognized type
     */
    public AgenticExecutor<? extends Agentic> initialize(@Nonnull Agentic agentic,
                                                         @Nullable Team partOf,
                                                         @Nonnull ContextStateManager stateManager,
                                                         @Nonnull ContextManager contextManager,
                                                         @Nonnull HandoffExecutor handoffExecutor) {


        return switch (agentic) {
            case Agent agent -> initializeAgentExecutor(agent, partOf, contextManager, handoffExecutor);
            case Team team -> initializeTeamExecutor(team, partOf, stateManager, contextManager, handoffExecutor);
            default -> throw new UnsupportedOperationException("Unrecognized agentic type " + agentic.getClass());
        };
    }

    private AgentExecutor initializeAgentExecutor(@Nonnull Agent agent,
                                                  @Nullable Team partOf,
                                                  @Nonnull ContextManager contextManager,
                                                  @Nonnull HandoffExecutor handoffExecutor) {

        final DefaultAgentExecutor executor = new DefaultAgentExecutor(agent, agenticModule, contextManager, handoffExecutor);

        registrar.register(agent, partOf, executor);

        return executor;
    }

    private TeamExecutor initializeTeamExecutor(@Nonnull Team team,
                                                @Nullable Team partOf,
                                                @Nonnull ContextStateManager stateManager,
                                                @Nonnull ContextManager contextManager,
                                                @Nonnull HandoffExecutor handoffExecutor) {
        return switch (team.mode()) {
            case route -> initializeRoundRobinTeamExecutor(team, partOf, stateManager, contextManager, handoffExecutor);
        };
    }

    private TeamExecutor initializeRoundRobinTeamExecutor(@Nonnull Team team,
                                                          @Nullable Team partOf,
                                                          @Nonnull ContextStateManager stateManager,
                                                          @Nonnull ContextManager contextManager,
                                                          @Nonnull HandoffExecutor handoffExecutor) {

        final RoutingModeTeamExecutor executor = new RoutingModeTeamExecutor(team, stateManager, contextManager,
                this, handoffExecutor);

        registrar.register(team, partOf, executor);

        return executor;
    }

}
