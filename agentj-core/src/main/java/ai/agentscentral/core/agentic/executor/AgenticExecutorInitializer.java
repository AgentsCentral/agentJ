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
 * AgenticExecutorInitializer
 *
 * @author Rizwan Idrees
 */
public class AgenticExecutorInitializer {

    private final AgenticModule agenticModule;
    private final AgenticRegistrar registrar;

    public AgenticExecutorInitializer(AgenticModule agenticModule,
                                      AgenticRegistrar registrar) {
        this.agenticModule = agenticModule;
        this.registrar = registrar;
    }


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
