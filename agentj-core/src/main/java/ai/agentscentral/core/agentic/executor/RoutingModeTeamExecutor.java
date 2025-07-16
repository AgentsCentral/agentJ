package ai.agentscentral.core.agentic.executor;

import ai.agentscentral.core.agent.Agent;
import ai.agentscentral.core.agentic.Agentic;
import ai.agentscentral.core.context.ContextManager;
import ai.agentscentral.core.context.ContextStateManager;
import ai.agentscentral.core.factory.AgentJFactory;
import ai.agentscentral.core.session.message.Message;
import ai.agentscentral.core.session.user.User;
import ai.agentscentral.core.team.Team;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * RoutingModeTeamExecutor
 *
 * @author Rizwan Idrees
 */
public class RoutingModeTeamExecutor implements TeamExecutor {

    private final Team team;
    private final Agent leader;
    private final List<Agentic> members;
    private final ContextStateManager stateManager;
    private final ContextManager contextManager;
    private final AgenticExecutorInitializer executorInitializer;
    private final Map<String, AgenticExecutor<? extends Agentic>> agentExecutors = new HashMap<>();
    private final HandoffExecutor handoffExecutor;

    public RoutingModeTeamExecutor(Team team,
                                   ContextStateManager stateManager,
                                   ContextManager contextManager,
                                   AgentJFactory agentJFactory,
                                   AgenticExecutorInitializer executorInitializer,
                                   HandoffExecutor handoffExecutor) {
        this.team = team;
        this.leader = team.leader();
        this.members = team.members();
        this.stateManager = stateManager;
        this.contextManager = contextManager;
        this.executorInitializer = executorInitializer;
        this.handoffExecutor = handoffExecutor;

        agentExecutors.put(leader.name(), initializeExecutor(leader, agentJFactory));
        members.forEach(a -> agentExecutors.put(a.name(), initializeExecutor(a, agentJFactory)));
    }


    @Override
    public List<Message> execute(String contextId,
                                 User user,
                                 List<Message> previousContext,
                                 List<Message> newMessages,
                                 Agentic currentAgentic,
                                 MessageExecutionContext executionContext) {

        final AgenticExecutor<? extends Agentic> agenticExecutor = findExecutor(currentAgentic)
                .orElse(agentExecutors.get(leader.name()));


        return agenticExecutor.execute(contextId, user, previousContext,
                newMessages, currentAgentic, executionContext);
    }


    @Override
    public Team getAgentic() {
        return this.team;
    }

    final Optional<AgenticExecutor<? extends Agentic>> findExecutor(Agentic agentic) {
        return ofNullable(agentic)
                .map(a -> agentExecutors.get(a.name()));
    }

    private AgenticExecutor<? extends Agentic> initializeExecutor(Agentic agentic, AgentJFactory agentJFactory) {
        return executorInitializer.initialize(agentic, team, stateManager, contextManager,
                handoffExecutor, agentJFactory);
    }

}
