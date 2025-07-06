package ai.agentscentral.core.conversation;

import ai.agentscentral.core.agent.*;
import ai.agentscentral.core.conversation.config.MessageLimits;
import ai.agentscentral.core.conversation.context.ConversationContextManager;
import ai.agentscentral.core.conversation.message.*;
import ai.agentscentral.core.conversation.state.ConversationStateManager;
import ai.agentscentral.core.conversation.state.DefaultConversationState;
import ai.agentscentral.core.handoff.Handoff;
import ai.agentscentral.core.handoff.HandoffInstruction;
import ai.agentscentral.core.tool.ToolCallExecutor;
import ai.agentscentral.core.tool.ToolCallInstruction;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static ai.agentscentral.core.conversation.message.MessagePartType.text;
import static ai.agentscentral.core.tool.convertors.ToolConvertors.convertToolCallExecutionErrorToToolMessage;
import static ai.agentscentral.core.tool.convertors.ToolConvertors.convertToolCallResultToToolMessage;
import static java.lang.System.currentTimeMillis;
import static java.util.Comparator.comparing;
import static java.util.Optional.ofNullable;

/**
 * DefaultConversationProcessor
 *
 * @author Rizwan Idrees
 */
public class DefaultConversationProcessor implements ConversationProcessor {

    private static final Logger logger = LoggerFactory.getLogger(DefaultConversationProcessor.class);

    private final AgentExecutorInitializer executorInitializer = new AgentExecutorInitializer();
    private final Agent defaultAgent;
    private final List<Agent> otherAgents;
    private final Map<String, AgentExecutor> agentExecutors = new HashMap<>();
    private final ConversationStateManager stateManager;
    private final ConversationContextManager contextManager;
    private final MessageIdGenerator messageIdGenerator;
    private final ToolCallExecutor<ToolMessage> toolCallExecutor;
    private final MessageLimits messageLimits;

    public DefaultConversationProcessor(AgentSystem agentSystem,
                                        ConversationStateManager stateManager,
                                        ConversationContextManager contextManager,
                                        MessageIdGenerator messageIdGenerator,
                                        ToolCallExecutor<ToolMessage> toolCallExecutor,
                                        MessageLimits messageLimits) {

        this.defaultAgent = agentSystem.defaultAgent();
        this.otherAgents = agentSystem.otherAgents();
        this.stateManager = stateManager;
        this.contextManager = contextManager;
        this.messageIdGenerator = messageIdGenerator;
        this.toolCallExecutor = toolCallExecutor;
        this.messageLimits = messageLimits;

        agentExecutors.put(defaultAgent.name(), executorInitializer.initialize(defaultAgent));

        if (Objects.nonNull(otherAgents)) {
            otherAgents.forEach(a -> agentExecutors.put(a.name(), executorInitializer.initialize(a)));
        }
    }

    @Override
    public List<AssistantMessage> process(@Nullable String conversationId, @Nonnull UserMessage message) {

        final MessageContext messageContext = new MessageContext();
        final List<Message> context = new ArrayList<>(contextManager.getContext(conversationId));
        context.sort(comparing(Message::timestamp));
        context.add(message);

        final AgentExecutor executor = ofNullable(conversationId)
                .flatMap(stateManager::getCurrentState)
                .map(state -> findAgentExecutor(state.currentAgent()))
                .orElse(agentExecutors.get(defaultAgent.name()));


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
                                                AgentExecutor executor) {


        if (messageContext.getHandOffCount() >= messageLimits.maxHandOffsPerMessage() ||
                messageContext.getToolCallsCount() >= messageLimits.maxToolCallsPerMessage()) {
            return context;
        }

        if (executor.getAgent() instanceof SimpleAgent) {
            return executeAIAgent(conversationId, context, messageContext, executor);
        }

        return List.of();
    }

    private List<? extends Message> executeAIAgent(String conversationId,
                                                   List<Message> context,
                                                   MessageContext messageContext,
                                                   AgentExecutor executor) {

        final List<AssistantMessage> assistantMessages = executor.process(conversationId, null, context);
        final boolean hasHandOffs = assistantMessages.stream().anyMatch(AssistantMessage::hasHandOffs);
        final boolean hasToolCalls = assistantMessages.stream().anyMatch(AssistantMessage::hasToolCalls);

        contextManager.addContext(conversationId, assistantMessages);
        context.addAll(assistantMessages);

        if (hasHandOffs) {

            final Optional<HandoffInstruction> handoffInstruction = assistantMessages.stream().filter(AssistantMessage::hasHandOffs)
                    .flatMap(m -> m.handoffs().stream()).findFirst();

            final Optional<Handoff> handoff = handoffInstruction.map(HandoffInstruction::handoff);

            handoff.map(h -> new DefaultConversationState(conversationId, h.agentName()))
                    .ifPresent(stateManager::updateState);

            final AgentExecutor handOffAgentExecutor = handoff.map(h -> findAgentExecutor(h.agentName()))
                    .orElse(agentExecutors.get(defaultAgent.name()));


            final String handOffId = handoffInstruction.map(HandoffInstruction::callId).orElse(null);
            final String handOffToAgentName = handoff.map(Handoff::agentName).orElse(null);
            final String handOffDescription = handoff.map(Handoff::description).orElse(null);
            final TextPart[] handOffText = new TextPart[]{new TextPart(text, handOffDescription)};


            final HandOffMessage handOffMessage = new HandOffMessage(conversationId, messageIdGenerator.generate(),
                    handOffId, handOffToAgentName, handOffText, currentTimeMillis());

            contextManager.addContext(conversationId, List.of(handOffMessage));
            context.add(handOffMessage);

            messageContext.incrementHandOffCount();

            return doExecution(conversationId, context, messageContext, handOffAgentExecutor);

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

    private AgentExecutor findAgentExecutor(String agentName) {
        return otherAgents.stream().filter(a -> a.name().equals(agentName))
                .findFirst().map(a -> agentExecutors.get(a.name())).orElse(agentExecutors.get(defaultAgent.name()));
    }

}
