package ai.agentscentral.core.agentic.executor;

import ai.agentscentral.core.agent.Agent;
import ai.agentscentral.core.agentic.Agentic;
import ai.agentscentral.core.context.ContextManager;
import ai.agentscentral.core.context.ContextStateManager;
import ai.agentscentral.core.session.message.Message;
import ai.agentscentral.core.session.message.UserMessage;
import ai.agentscentral.core.session.user.User;
import ai.agentscentral.core.team.Team;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * {@link TeamExecutor} implementation for teams operating in
 * {@link ai.agentscentral.core.team.TeamMode#route route} mode.
 *
 * <p>On construction, an {@link AgenticExecutor} is initialised for each member of the
 * team (including the leader) and stored in an internal map keyed by agent name. On each
 * {@link #execute} call, the executor whose name matches {@code currentAgenticName} is
 * selected; if no match is found the leader's executor is used as the default.</p>
 *
 * @author Rizwan Idrees
 */
public class RoutingModeTeamExecutor implements TeamExecutor {

    private final Team team;
    private final Agent leader;
    private final ContextStateManager stateManager;
    private final ContextManager contextManager;
    private final AgenticExecutorInitializer executorInitializer;
    private final Map<String, AgenticExecutor<? extends Agentic>> agentExecutors = new HashMap<>();
    private final HandoffExecutor handoffExecutor;

    /**
     * Creates a new {@code RoutingModeTeamExecutor} and eagerly initialises an executor
     * for every team member (leader + members).
     *
     * @param team              the team to execute
     * @param stateManager      tracks which agent is currently active in the conversation
     * @param contextManager    persists messages produced during execution
     * @param executorInitializer factory used to create member executors
     * @param handoffExecutor   handles handoffs between agents within the team
     */
    public RoutingModeTeamExecutor(Team team,
                                   ContextStateManager stateManager,
                                   ContextManager contextManager,
                                   AgenticExecutorInitializer executorInitializer,
                                   HandoffExecutor handoffExecutor) {
        this.team = team;
        this.leader = team.leader();
        this.stateManager = stateManager;
        this.contextManager = contextManager;
        this.executorInitializer = executorInitializer;
        this.handoffExecutor = handoffExecutor;

        Optional.of(team.leader())
                .ifPresent(l -> agentExecutors.put(l.name(), initializeExecutor(leader)));

        team.members().forEach(a -> agentExecutors.put(a.name(), initializeExecutor(a)));
    }


    @Override
    public List<Message> execute(String contextId,
                                 User user,
                                 UserMessage userMessage,
                                 List<Message> previousContext,
                                 List<Message> newMessages,
                                 String currentAgenticName,
                                 MessageExecutionContext executionContext) {

        final AgenticExecutor<? extends Agentic> agenticExecutor = findExecutor(currentAgenticName)
                .orElse(agentExecutors.get(leader.name()));

        return agenticExecutor.execute(contextId, user, userMessage, previousContext, newMessages,
                currentAgenticName, executionContext);
    }


    @Override
    public Team getAgentic() {
        return this.team;
    }

    /**
     * Looks up the executor registered under {@code agenticName}.
     *
     * @param agenticName name of the agent or team member to find; may be {@code null}
     * @return an {@code Optional} containing the matching executor, or empty if not found
     */
    final Optional<AgenticExecutor<? extends Agentic>> findExecutor(String agenticName) {
        return ofNullable(agenticName).map(agentExecutors::get);
    }

    private AgenticExecutor<? extends Agentic> initializeExecutor(Agentic agentic) {
        return executorInitializer.initialize(agentic, team, stateManager, contextManager, handoffExecutor);
    }

}
