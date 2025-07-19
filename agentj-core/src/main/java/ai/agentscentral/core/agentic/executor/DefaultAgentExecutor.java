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
import ai.agentscentral.core.tool.ToolCallExecutor;
import ai.agentscentral.core.tool.ToolCallInstruction;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ai.agentscentral.core.session.message.MessagePartType.text;
import static ai.agentscentral.core.tool.convertors.ToolConvertors.convertToolCallExecutionErrorToToolMessage;
import static ai.agentscentral.core.tool.convertors.ToolConvertors.convertToolCallResultToToolMessage;
import static java.lang.System.currentTimeMillis;

public class DefaultAgentExecutor implements AgentExecutor {

    private final Agent agent;
    private final ContextManager contextManager;
    private final HandoffExecutor handoffExecutor;
    private final ToolCallExecutor<ToolMessage> toolCallExecutor;
    private final ProviderAgentExecutor providerAgentExecutor;
    private final MessageIdGenerator messageIdGenerator;

    public DefaultAgentExecutor(Agent agent,
                                AgentJFactory agentJFactory,
                                ContextManager contextManager,
                                HandoffExecutor handoffExecutor) {
        this.agent = agent;
        this.toolCallExecutor = agentJFactory.getToolCallExecutor();
        this.contextManager = contextManager;
        this.handoffExecutor = handoffExecutor;
        this.providerAgentExecutor = agent.model().config()
                .getFactory().createAgentExecutor(agent, agentJFactory);

        this.messageIdGenerator = agentJFactory.getMessageIdGenerator();

    }

    @Override
    public List<Message> execute(@Nonnull String contextId,
                                 @Nullable User user,
                                 List<Message> previousContext,
                                 List<Message> newMessages,
                                 String currentAgenticName,
                                 MessageExecutionContext executionContext) {

        final List<Message> localContext = new ArrayList<>(previousContext);
        localContext.addAll(newMessages);

        final List<AssistantMessage> assistantMessages = providerAgentExecutor.execute(contextId, user, localContext);

        final boolean hasHandOffs = assistantMessages.stream().anyMatch(AssistantMessage::hasHandOffs);
        final boolean hasToolCalls = assistantMessages.stream().anyMatch(AssistantMessage::hasToolCalls);

        contextManager.addContext(contextId, assistantMessages);
        newMessages.addAll(assistantMessages);
        localContext.addAll(assistantMessages);


        if (executionContext.isHandoffLimitExceeded() || executionContext.isToolCallLimitExceeded()) {
            return newMessages;
        }


        if (hasHandOffs) {

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

            return handedOff.newAgenticExecutor().execute(contextId, user, previousContext, newMessages,
                    handedOff.agent(), executionContext);

        } else if (hasToolCalls) {

            final List<ToolCallInstruction> toolCallInstructions = assistantMessages.stream()
                    .filter(AssistantMessage::hasToolCalls)
                    .flatMap(m -> m.toolCalls().stream()).toList();

            final List<ToolMessage> toolMessages = toolCallInstructions.stream()
                    .map(toolCallExecutor::execute)
                    .map(roe -> roe.onResult(contextId, messageIdGenerator.generate(), convertToolCallResultToToolMessage)
                            .orOnError(convertToolCallExecutionErrorToToolMessage)
                    ).toList();

            contextManager.addContext(contextId, toolMessages);
            newMessages.addAll(toolMessages);
            localContext.addAll(toolMessages);

            executionContext.incrementToolCalls();

            return execute(contextId, user, previousContext, newMessages, currentAgenticName, executionContext);
        }

        return newMessages;
    }


    @Override
    public Agent getAgentic() {
        return this.agent;
    }
}
