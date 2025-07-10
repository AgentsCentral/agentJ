package ai.agentscentral.core.session.processor;

import ai.agentscentral.core.agent.*;
import ai.agentscentral.core.agentic.Agentic;
import ai.agentscentral.core.agentic.executor.AgentExecutorInitializer;
import ai.agentscentral.core.agentic.executor.AgenticExecutor;
import ai.agentscentral.core.session.config.MessageLimits;
import ai.agentscentral.core.session.context.SessionContextManager;
import ai.agentscentral.core.session.id.MessageIdGenerator;
import ai.agentscentral.core.session.message.*;
import ai.agentscentral.core.session.state.SessionStateManager;
import ai.agentscentral.core.session.state.DefaultSessionState;
import ai.agentscentral.core.handoff.Handoff;
import ai.agentscentral.core.handoff.HandoffInstruction;
import ai.agentscentral.core.session.user.User;
import ai.agentscentral.core.team.Team;
import ai.agentscentral.core.tool.ToolCallExecutor;
import ai.agentscentral.core.tool.ToolCallInstruction;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static ai.agentscentral.core.session.message.MessagePartType.text;
import static ai.agentscentral.core.tool.convertors.ToolConvertors.convertToolCallExecutionErrorToToolMessage;
import static ai.agentscentral.core.tool.convertors.ToolConvertors.convertToolCallResultToToolMessage;
import static java.lang.System.currentTimeMillis;
import static java.util.Comparator.comparing;
import static java.util.Optional.ofNullable;

/**
 * DefaultSessionProcessor
 *
 * @author Rizwan Idrees
 */
public class DefaultSessionProcessor implements SessionProcessor {

    private static final Logger logger = LoggerFactory.getLogger(DefaultSessionProcessor.class);

    private final AgentExecutorInitializer executorInitializer = new AgentExecutorInitializer();
    private final Agentic defaultAgentic;
    private final List<Agentic> otherAgentics;
    private final Map<String, AgenticExecutor> agentExecutors = new HashMap<>();
    private final SessionStateManager stateManager;
    private final SessionContextManager contextManager;
    private final MessageIdGenerator messageIdGenerator;
    private final ToolCallExecutor<ToolMessage> toolCallExecutor;
    private final MessageLimits messageLimits;

    public DefaultSessionProcessor(Team team,
                                   SessionStateManager stateManager,
                                   SessionContextManager contextManager,
                                   MessageIdGenerator messageIdGenerator,
                                   ToolCallExecutor<ToolMessage> toolCallExecutor,
                                   MessageLimits messageLimits) {

        this.defaultAgentic = team.leader();
        this.otherAgentics = team.members();
        this.stateManager = stateManager;
        this.contextManager = contextManager;
        this.messageIdGenerator = messageIdGenerator;
        this.toolCallExecutor = toolCallExecutor;
        this.messageLimits = messageLimits;

        agentExecutors.put(defaultAgentic.name(), executorInitializer.initialize(defaultAgentic));

        if (Objects.nonNull(otherAgentics)) {
            otherAgentics.forEach(a -> agentExecutors.put(a.name(), executorInitializer.initialize(a)));
        }
    }

    @Override
    public List<AssistantMessage> process(@Nullable String conversationId, @Nonnull UserMessage message, User user) {

        final MessageContext messageContext = new MessageContext();
        final List<Message> context = new ArrayList<>(contextManager.getContext(conversationId));
        context.sort(comparing(Message::timestamp));
        context.add(message);

        final AgenticExecutor executor = ofNullable(conversationId)
                .flatMap(stateManager::getCurrentState)
                .map(state -> findAgentExecutor(state.currentAgent()))
                .orElse(agentExecutors.get(defaultAgentic.name()));


        final List<? extends Message> messages = doExecution(conversationId, context, messageContext, executor);

        return messages.stream()
                .filter(m -> m instanceof AssistantMessage)
                .map(m -> (AssistantMessage) m)
                .filter(am -> !am.hasToolCalls() && !am.hasHandOffs())
                .filter(am -> am.timestamp() > message.timestamp())
                .toList();
    }

    private List<? extends Message> doExecution(String conversationId,
                                                List<Message> context,
                                                MessageContext messageContext,
                                                AgenticExecutor executor) {


        if (messageContext.getHandOffCount() >= messageLimits.maxHandOffsPerMessage() ||
                messageContext.getToolCallsCount() >= messageLimits.maxToolCallsPerMessage()) {
            return context;
        }

        if (executor.getAgentic() instanceof Agent) {
            return executeAIAgent(conversationId, context, messageContext, executor);
        }

        return List.of();
    }

    private List<? extends Message> executeAIAgent(String conversationId,
                                                   List<Message> context,
                                                   MessageContext messageContext,
                                                   AgenticExecutor executor) {

        final List<AssistantMessage> assistantMessages = executor.process(conversationId, null, context);
        final boolean hasHandOffs = assistantMessages.stream().anyMatch(AssistantMessage::hasHandOffs);
        final boolean hasToolCalls = assistantMessages.stream().anyMatch(AssistantMessage::hasToolCalls);

        contextManager.addContext(conversationId, assistantMessages);
        context.addAll(assistantMessages);

        if (hasHandOffs) {

            final Optional<HandoffInstruction> handoffInstruction = assistantMessages.stream().filter(AssistantMessage::hasHandOffs)
                    .flatMap(m -> m.handoffs().stream()).findFirst();

            final Optional<Handoff> handoff = handoffInstruction.map(HandoffInstruction::handoff);

            handoff.map(h -> new DefaultSessionState(conversationId, h.agentName()))
                    .ifPresent(stateManager::updateState);

            final AgenticExecutor handOffAgenticExecutor = handoff.map(h -> findAgentExecutor(h.agentName()))
                    .orElse(agentExecutors.get(defaultAgentic.name()));


            final String handOffId = handoffInstruction.map(HandoffInstruction::callId).orElse(null);
            final String handOffToAgentName = handoff.map(Handoff::agentName).orElse(null);
            final String handOffDescription = handoff.map(Handoff::description).orElse(null);
            final TextPart[] handOffText = new TextPart[]{new TextPart(text, handOffDescription)};


            final HandOffMessage handOffMessage = new HandOffMessage(conversationId, messageIdGenerator.generate(),
                    handOffId, handOffToAgentName, handOffText, currentTimeMillis());

            contextManager.addContext(conversationId, List.of(handOffMessage));
            context.add(handOffMessage);

            messageContext.incrementHandOffCount();

            return doExecution(conversationId, context, messageContext, handOffAgenticExecutor);

        } else if (hasToolCalls) {

            final List<ToolCallInstruction> toolCallInstructions = assistantMessages.stream()
                    .filter(AssistantMessage::hasToolCalls)
                    .flatMap(m -> m.toolCalls().stream()).toList();

            final List<ToolMessage> toolMessages = toolCallInstructions.stream()
                    .map(toolCallExecutor::execute)
                    .map(roe -> roe.onResult(conversationId, messageIdGenerator.generate(), convertToolCallResultToToolMessage)
                            .orOnError(convertToolCallExecutionErrorToToolMessage)
                    ).toList();

            contextManager.addContext(conversationId, toolMessages);
            context.addAll(toolMessages);

            messageContext.incrementToolCalls();

            return doExecution(conversationId, context, messageContext, executor);
        }

        return assistantMessages;
    }

    private AgenticExecutor findAgentExecutor(String agentName) {
        return otherAgentics.stream().filter(a -> a.name().equals(agentName))
                .findFirst().map(a -> agentExecutors.get(a.name())).orElse(agentExecutors.get(defaultAgentic.name()));
    }

}
