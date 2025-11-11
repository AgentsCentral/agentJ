package ai.agentscentral.core.agentic.executor;

import ai.agentscentral.core.agent.Agent;
import ai.agentscentral.core.context.ContextManager;
import ai.agentscentral.core.factory.AgentJFactory;
import ai.agentscentral.core.handoff.Handoff;
import ai.agentscentral.core.handoff.HandoffInstruction;
import ai.agentscentral.core.provider.ProviderAgentExecutor;
import ai.agentscentral.core.session.id.MessageIdGenerator;
import ai.agentscentral.core.session.message.*;
import ai.agentscentral.core.session.user.User;
import ai.agentscentral.core.tool.*;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ai.agentscentral.core.session.message.MessagePartType.text;
import static ai.agentscentral.core.session.message.MessagePartType.tool_interrupt;
import static ai.agentscentral.core.tool.convertors.ToolConvertors.convertToolCallExecutionErrorToToolMessage;
import static ai.agentscentral.core.tool.convertors.ToolConvertors.convertToolCallResultToToolMessage;
import static java.lang.System.currentTimeMillis;
import static java.util.stream.Collectors.groupingBy;

/**
 * DefaultAgentExecutor
 *
 * @author Rizwan Idrees
 */
public class DefaultAgentExecutor implements AgentExecutor {

    private final Agent agent;
    private final ContextManager contextManager;
    private final HandoffExecutor handoffExecutor;
    private final ToolCallExecutor<ToolMessage> toolCallExecutor;
    private final ProviderAgentExecutor providerAgentExecutor;
    private final MessageIdGenerator messageIdGenerator;
    private final Map<String, ToolCall> tools;

    public DefaultAgentExecutor(Agent agent,
                                AgentJFactory agentJFactory,
                                ContextManager contextManager,
                                HandoffExecutor handoffExecutor) {
        this.agent = agent;
        this.tools = agentJFactory.getToolBagToolsExtractor().extractTools(agent.toolBags());
        this.toolCallExecutor = agentJFactory.getToolCallExecutor();
        this.contextManager = contextManager;
        this.handoffExecutor = handoffExecutor;


        final Map<String, Handoff> handOffs = agentJFactory.getHandoffsExtractor()
                .extractHandOffs(agent.handoffs());

        this.providerAgentExecutor = agent.model().config()
                .getFactory().createAgentExecutor(agent, tools, handOffs);

        this.messageIdGenerator = agentJFactory.getMessageIdGenerator();

    }

    @Override
    public List<Message> execute(@Nonnull String contextId,
                                 @Nullable User user,
                                 UserMessage userMessage,
                                 List<Message> previousContext,
                                 List<Message> newMessages,
                                 String currentAgenticName,
                                 MessageExecutionContext executionContext) {

        final List<Message> localContext = new ArrayList<>(previousContext);
        localContext.addAll(newMessages);

        if (executionContext.isHandoffLimitExceeded() || executionContext.isToolCallLimitExceeded()) {
            return newMessages;
        }

        if (shouldResumeToolCall(userMessage, executionContext)) {
            return resumeToolCallsWithInterrupt(contextId, user, userMessage, previousContext,
                    newMessages, currentAgenticName, executionContext);
        }

        final List<AssistantMessage> assistantMessages = providerAgentExecutor.execute(contextId, user, localContext);

        final boolean hasHandOffs = assistantMessages.stream().anyMatch(AssistantMessage::hasHandOffs);
        final boolean hasToolCalls = assistantMessages.stream().anyMatch(AssistantMessage::hasToolCalls);
        final boolean toolCallHasInterrupts = hasToolCalls && assistantMessages.stream()
                .flatMap(am -> am.toolCalls().stream())
                .map(tci -> tools.get(tci.name())).anyMatch(ToolCall::hasInterruptsBefore);

        contextManager.addContext(contextId, assistantMessages);
        newMessages.addAll(assistantMessages);
        localContext.addAll(assistantMessages);


        if (hasHandOffs) {
            return handleHandOff(contextId, user, userMessage, previousContext, newMessages, executionContext,
                    assistantMessages, localContext);
        } else if (hasToolCalls && toolCallHasInterrupts) {
            return handleToolCallInterrupts(contextId, user, newMessages, assistantMessages);
        } else if (hasToolCalls) {
            return handleToolCalls(contextId, user, userMessage, previousContext, newMessages, currentAgenticName,
                    executionContext, assistantMessages);
        }

        return newMessages;
    }


    private List<Message> handleToolCallInterrupts(String contextId,
                                                   User user,
                                                   List<Message> newMessages,
                                                   List<AssistantMessage> assistantMessages) {

        final List<ToolCallInstruction> toolCallInstructions = assistantMessages.stream()
                .filter(AssistantMessage::hasToolCalls)
                .flatMap(am -> am.toolCalls().stream())
                .filter(tci -> tools.get(tci.name()).hasInterruptsBefore()).toList();


        final MessagePart[] toolInterruptParts = toolCallInstructions.stream()
                .filter(tci -> tools.get(tci.name()).hasInterruptsBefore())
                .flatMap(tci -> toolInterruptParts(tci, user).stream())
                .toList().toArray(new MessagePart[]{});


        final ToolInterruptMessage toolInterruptMessage = new ToolInterruptMessage(contextId,
                messageIdGenerator.generate(), toolInterruptParts, currentTimeMillis());

        contextManager.addContext(contextId, List.of(toolInterruptMessage));
        newMessages.add(toolInterruptMessage);

        return newMessages;
    }


    private List<Message> resumeToolCallsWithInterrupt(String contextId,
                                                       User user,
                                                       UserMessage userMessage,
                                                       List<Message> previousContext,
                                                       List<Message> newMessages,
                                                       String currentAgenticName,
                                                       MessageExecutionContext executionContext) {

        executionContext.markInterruptsAsProcessed();

        final List<UserInterruptPart> userInterruptParts = Stream.of(userMessage.parts())
                .filter(p -> p instanceof UserInterruptPart)
                .map(p -> (UserInterruptPart) p)
                .toList();

        final Set<String> interruptedToolCallIds = userInterruptParts.stream()
                .map(UserInterruptPart::toolCallId).collect(Collectors.toSet());

        final Map<String, List<UserInterruptPart>> interruptsByToolCallId = userInterruptParts.stream()
                .collect(groupingBy(UserInterruptPart::toolCallId));

        final List<ToolCallInstruction> toolCallInstructions = previousContext.stream()
                .filter(m -> m instanceof AssistantMessage)
                .map(m -> (AssistantMessage) m)
                .filter(AssistantMessage::hasToolCalls)
                .flatMap(m -> m.toolCalls().stream())
                .filter(tci -> interruptedToolCallIds.contains(tci.id()))
                .toList();


        final List<ToolMessage> toolMessages = toolCallInstructions.stream()
                .map(tci -> toolCallExecutor.execute(tci, tools.get(tci.name()),
                        interruptParameterValues(interruptsByToolCallId.getOrDefault(tci.id(), List.of()))))
                .map(roe -> roe.onResult(contextId, messageIdGenerator.generate(), convertToolCallResultToToolMessage)
                        .orOnError(convertToolCallExecutionErrorToToolMessage)
                ).toList();

        contextManager.addContext(contextId, toolMessages);
        newMessages.addAll(toolMessages);

        executionContext.incrementToolCalls();

        return execute(contextId, user, userMessage, previousContext, newMessages, currentAgenticName, executionContext);
    }

    private List<Message> handleToolCalls(String contextId,
                                          User user,
                                          UserMessage userMessage,
                                          List<Message> previousContext,
                                          List<Message> newMessages,
                                          String currentAgenticName,
                                          MessageExecutionContext executionContext,
                                          List<AssistantMessage> assistantMessages) {

        final List<ToolCallInstruction> toolCallInstructions = assistantMessages.stream()
                .filter(AssistantMessage::hasToolCalls)
                .flatMap(m -> m.toolCalls().stream()).toList();


        final List<ToolMessage> toolMessages = toolCallInstructions.stream()
                .map(tci -> toolCallExecutor.execute(tci, tools.get(tci.name()), List.of()))
                .map(roe -> roe.onResult(contextId, messageIdGenerator.generate(), convertToolCallResultToToolMessage)
                        .orOnError(convertToolCallExecutionErrorToToolMessage)
                ).toList();

        contextManager.addContext(contextId, toolMessages);
        newMessages.addAll(toolMessages);

        executionContext.incrementToolCalls();

        return execute(contextId, user, userMessage, previousContext, newMessages, currentAgenticName, executionContext);
    }

    private List<Message> handleHandOff(String contextId,
                                        User user,
                                        UserMessage userMessage,
                                        List<Message> previousContext,
                                        List<Message> newMessages,
                                        MessageExecutionContext executionContext,
                                        List<AssistantMessage> assistantMessages,
                                        List<Message> localContext) {

        final Optional<HandoffInstruction> handoffInstruction = assistantMessages.stream()
                .filter(AssistantMessage::hasHandOffs)
                .flatMap(m -> m.handoffs().stream()).findFirst();


        final HandedOff handedOff = handoffInstruction
                .map(hi -> handoffExecutor.handoff(contextId, hi))
                .orElseThrow(() -> new UnsupportedOperationException("unable to find the agent"));


        final Optional<Handoff> handoff = handoffInstruction.map(HandoffInstruction::handoff);

        final String handOffId = handoffInstruction.map(HandoffInstruction::callId).orElse(null);
        final String handOffToAgentName = handoff.map(Handoff::agenticName).orElse(null);
        final String handOffDescription = handoff.map(Handoff::description).orElse(null);
        final TextPart[] handOffText = new TextPart[]{new TextPart(text, handOffDescription)};


        final HandOffMessage handOffMessage = new HandOffMessage(contextId, messageIdGenerator.generate(),
                handOffId, handOffToAgentName, handOffText, currentTimeMillis());

        contextManager.addContext(contextId, List.of(handOffMessage));
        localContext.add(handOffMessage);
        newMessages.add(handOffMessage);

        final List<AssistantMessage> postHandOffMessages = providerAgentExecutor.execute(contextId, user, localContext);
        newMessages.addAll(postHandOffMessages);
        contextManager.addContext(contextId, postHandOffMessages);

        executionContext.incrementHandOffCount();

        return handedOff.newAgenticExecutor().execute(contextId, user, userMessage, previousContext, newMessages,
                handedOff.agent(), executionContext);
    }


    private List<ToolInterruptPart> toolInterruptParts(ToolCallInstruction toolCallInstruction, User user) {

        final Map<String, List<String>> interruptPreCalls = Optional.of(toolCallInstruction).stream()
                .filter(tci -> tools.get(tci.name()).hasInterruptsBefore())
                .flatMap(tci -> tools.get(tci.name()).interruptsBefore().stream())
                .filter(ti -> Objects.nonNull(ti.preInterruptCalls()))
                .collect(Collectors.toMap(ToolInterrupt::name, ToolInterrupt::preInterruptCalls));


        final ToolCall toolCall = tools.get(toolCallInstruction.name());
        return toolCall.interruptsBefore().stream()
                .map(i -> new ToolInterruptPart(tool_interrupt,
                        toolCallInstruction.id(),
                        i.name(),
                        i.renderer(),
                        toolCallInstruction.arguments(),
                        interruptPreCallResults(interruptPreCalls.getOrDefault(i.name(), List.of()), user),
                        toolInterruptParameters(i.parameters())
                )).toList();
    }


    private List<InterruptPreCallResult> interruptPreCallResults(List<String> preCalls, User user) {
        return preCalls.stream()
                .map(pc -> new InterruptPreCallResult(pc, toolCallExecutor.executePreCall(pc, user)))
                .toList();
    }

    private List<ToolInterruptParameter> toolInterruptParameters(List<InterruptParameter> interruptParameters) {
        return interruptParameters.stream().map(i -> new ToolInterruptParameter(i.name(), i.required()))
                .toList();
    }

    private List<InterruptParameterValue> interruptParameterValues(List<UserInterruptPart> interruptParts) {
        return interruptParts.stream()
                .flatMap(um -> um.interruptParameters().stream())
                .toList();
    }

    private boolean shouldResumeToolCall(UserMessage userMessage, MessageExecutionContext executionContext) {

        final boolean hasInterruptParts = Stream.of(userMessage.parts()).anyMatch(p -> p instanceof UserInterruptPart);
        return !executionContext.isInterruptsProcessed() && hasInterruptParts;
    }

    @Override
    public Agent getAgentic() {
        return this.agent;
    }
}
