package ai.agentscentral.core.session.processor;

import ai.agentscentral.core.agentic.Agentic;
import ai.agentscentral.core.agentic.executor.AgenticExecutor;
import ai.agentscentral.core.agentic.executor.AgenticExecutorInitializer;
import ai.agentscentral.core.agentic.executor.DefaultHandoffExecutor;
import ai.agentscentral.core.agentic.executor.MessageExecutionContext;
import ai.agentscentral.core.agentic.executor.register.AgenticRegistrar;
import ai.agentscentral.core.agentic.executor.register.DefaultAgenticRegistrar;
import ai.agentscentral.core.agentic.executor.register.RegisteredAgentic;
import ai.agentscentral.core.context.ContextManager;
import ai.agentscentral.core.context.ContextStateManager;
import ai.agentscentral.core.factory.AgentJFactory;
import ai.agentscentral.core.session.config.ExecutionLimits;
import ai.agentscentral.core.session.message.AssistantMessage;
import ai.agentscentral.core.session.message.Message;
import ai.agentscentral.core.session.message.ToolInterruptMessage;
import ai.agentscentral.core.session.message.UserMessage;
import ai.agentscentral.core.session.user.User;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Comparator.comparing;
import static java.util.Optional.of;

/**
 * DefaultSessionProcessor
 *
 * @author Rizwan Idrees
 */
public class DefaultSessionProcessor implements SessionProcessor {

    private static final Logger logger = LoggerFactory.getLogger(DefaultSessionProcessor.class);

    private final AgenticRegistrar registrar = new DefaultAgenticRegistrar();
    private final AgenticExecutor<? extends Agentic> agenticExecutor;
    private final ContextStateManager stateManager;
    private final ContextManager contextManager;
    private final ExecutionLimits executionLimits;


    public DefaultSessionProcessor(Agentic agentic,
                                   AgentJFactory agentJFactory,
                                   ContextStateManager stateManager,
                                   ContextManager contextManager,
                                   ExecutionLimits executionLimits) {

        this.stateManager = stateManager;
        this.contextManager = contextManager;
        this.agenticExecutor = initializeExecutor(agentic, agentJFactory);
        this.executionLimits = executionLimits;
    }

    @Override
    public List<Message> process(@Nonnull String sessionId, @Nonnull UserMessage message, User user) {

        contextManager.addContext(sessionId, List.of(message));

        final MessageExecutionContext executionContext = new MessageExecutionContext(executionLimits);
        final List<Message> context = new ArrayList<>(contextManager.getContext(sessionId));
        context.sort(comparing(Message::timestamp));

        final AgenticExecutor<? extends Agentic> executor = findExecutor(sessionId)
                .orElse(agenticExecutor);

        final List<Message> newMessages = executor.execute(sessionId, user, message,
                context, new ArrayList<>(), null, executionContext);

        return newMessages.stream()
                .filter(m -> isDisplayableMessage(m, message.timestamp()))
                .toList();
    }

    private AgenticExecutor<? extends Agentic> initializeExecutor(Agentic agentic, AgentJFactory agentJFactory) {
        final AgenticExecutorInitializer agenticExecutorInitializer = new AgenticExecutorInitializer(registrar);
        return agenticExecutorInitializer.initialize(agentic, null,
                stateManager, contextManager, new DefaultHandoffExecutor(registrar, stateManager), agentJFactory);
    }

    private Optional<AgenticExecutor<? extends Agentic>> findExecutor(String sessionId) {
        return of(sessionId)
                .flatMap(stateManager::getCurrentState)
                .flatMap(state -> registrar.find(state.currentAgent(), state.currentTeam()))
                .map(RegisteredAgentic::executor);
    }

    private boolean isDisplayableMessage(Message message, long timestampAfter) {
        if (message instanceof AssistantMessage am) {
            return !am.hasToolCalls() && !am.hasHandOffs() && message.timestamp() > timestampAfter;
        } else if (message instanceof ToolInterruptMessage interruptMessage) {
            return interruptMessage.timestamp() > timestampAfter;
        }

        return false;
    }

}
