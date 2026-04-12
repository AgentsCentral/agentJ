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
import ai.agentscentral.core.agentic.AgenticModule;
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
 * Default implementation of {@link SessionProcessor}.
 *
 * <p>On construction, the provided {@link ai.agentscentral.core.agentic.Agentic} (agent
 * or team) is fully initialised via {@link ai.agentscentral.core.agentic.executor.AgenticExecutorInitializer},
 * which wires all executors and registers them with an internal
 * {@link ai.agentscentral.core.agentic.executor.register.AgenticRegistrar}.</p>
 *
 * <p>On each call to {@link #process}, the processor:
 * <ol>
 *   <li>Persists the incoming {@link ai.agentscentral.core.session.message.UserMessage}.</li>
 *   <li>Retrieves and sorts the full conversation context by timestamp.</li>
 *   <li>Resolves the currently active executor from context state (falls back to the
 *       root executor if no state is recorded).</li>
 *   <li>Delegates execution to the resolved executor.</li>
 *   <li>Filters the resulting messages to return only those displayable to the caller
 *       (non-tool-call, non-handoff {@link ai.agentscentral.core.session.message.AssistantMessage}s
 *       and {@link ai.agentscentral.core.session.message.ToolInterruptMessage}s produced
 *       after the current user message timestamp).</li>
 * </ol>
 * </p>
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


    /**
     * Creates a new {@code DefaultSessionProcessor}, initialising all executors for the
     * given agentic entity.
     *
     * @param agentic         the root agent or team to execute; all members are recursively
     *                        initialised and registered at construction time
     * @param agentJModule    shared infrastructure module (context, session ids, tools,
     *                        handoffs)
     * @param executionLimits per-message limits on tool-call rounds and handoffs
     */
    public DefaultSessionProcessor(Agentic agentic,
                                   AgenticModule agentJModule,
                                   ExecutionLimits executionLimits) {

        this.stateManager = agentJModule.contextModule().contextStateManager();
        this.contextManager = agentJModule.contextModule().contextManager();
        this.agenticExecutor = initializeExecutor(agentic, agentJModule);
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

    private AgenticExecutor<? extends Agentic> initializeExecutor(Agentic agentic, AgenticModule agenticModule) {
        final AgenticExecutorInitializer agenticExecutorInitializer = new AgenticExecutorInitializer(agenticModule, registrar);
        return agenticExecutorInitializer.initialize(agentic, null,
                stateManager, contextManager, new DefaultHandoffExecutor(registrar, stateManager));
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
