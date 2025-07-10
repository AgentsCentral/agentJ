package ai.agentscentral.anthropic.processor;

import ai.agentscentral.anthropic.client.AnthropicClient;
import ai.agentscentral.anthropic.client.request.MessagesRequest;
import ai.agentscentral.anthropic.client.request.attributes.*;
import ai.agentscentral.anthropic.client.response.MessagesResponse;
import ai.agentscentral.anthropic.config.AnthropicConfig;
import ai.agentscentral.core.agent.Agent;
import ai.agentscentral.core.agentic.executor.AgenticExecutor;
import ai.agentscentral.core.agent.instructor.Instructor;
import ai.agentscentral.core.session.message.AssistantMessage;
import ai.agentscentral.core.session.message.Message;
import ai.agentscentral.core.factory.AgentJFactory;
import ai.agentscentral.core.handoff.Handoff;
import ai.agentscentral.core.session.user.User;
import ai.agentscentral.core.tool.ToolCall;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static ai.agentscentral.anthropic.client.request.attributes.TextPrompt.TYPE;
import static ai.agentscentral.anthropic.processor.AnthropicToolConvertor.handOffsToAnthropicTool;
import static ai.agentscentral.anthropic.processor.AnthropicToolConvertor.toolsToAnthropicTool;
import static java.util.Optional.ofNullable;

/**
 * AnthropicAgentExecutor
 *
 * @author Rizwan Idrees
 */
public class AnthropicAgentExecutor implements AgenticExecutor<Agent>{

    private static final AgentJFactory agentJFactory = AgentJFactory.getInstance();

    private final Agent agent;
    private final Map<String, AnthropicTool> anthropicTools = new HashMap<>();
    private final String modelName;
    private final AnthropicConfig config;
    private final AnthropicClient client;
    private final MessageConvertor messageConvert;

    public AnthropicAgentExecutor(Agent agent, AnthropicClient client) {
        this.agent = agent;
        this.modelName = agent.model().getModel();
        this.config = agent.model().getConfig() instanceof AnthropicConfig c ? c : null;
        this.client = client;
        final Map<String, ToolCall> tools = agentJFactory.getToolBagToolsExtractor().extractTools(agent.toolBags());
        final Map<String, Handoff> handOffs = agentJFactory.getHandoffsExtractor().extractHandOffs(agent.handoffs());
        final Map<String, AnthropicTool> mappedTools = toolsToAnthropicTool(tools);
        final Map<String, AnthropicTool> mappedHandOffs = handOffsToAnthropicTool(handOffs);

        ofNullable(mappedTools).ifPresent(anthropicTools::putAll);
        ofNullable(mappedHandOffs).ifPresent(anthropicTools::putAll);

        this.messageConvert = new MessageConvertor(tools, handOffs);
    }

    public List<AssistantMessage> process(String contextId, User user, List<Message> messages) {


        final SystemPrompt systemPrompt = systemPrompts();

        final MessagesRequest request = MessagesRequest.from(modelName,
                config,
                systemPrompt,
                user.id(),
                List.copyOf(anthropicTools.values()),
                messageConvert.toAnthropicMessages(messages));


        final MessagesResponse messagesResponse = client.sendMessages(request);

        return messagesResponse.content().stream()
                .map(cm -> messageConvert.toAssistantMessage(contextId, messagesResponse))
                .toList();
    }

    private SystemPrompt systemPrompts() {
        if (Objects.isNull(agent.instructors())) {
            return new TextSystemPrompt("You are an assistant");
        }

        final List<TextPrompt> textPrompts = agent.instructors().stream()
                .map(Instructor::instruct)
                .map(s -> new TextPrompt(TYPE, s))
                .toList();

        return new SystemPrompts(textPrompts);
    }

    @Override
    public Agent getAgentic() {
        return this.agent;
    }
}
