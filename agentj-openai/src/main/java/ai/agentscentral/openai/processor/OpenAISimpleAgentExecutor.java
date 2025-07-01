package ai.agentscentral.openai.processor;

import ai.agentscentral.core.agent.Agent;
import ai.agentscentral.core.agent.AgentExecutor;
import ai.agentscentral.core.agent.SimpleAgent;
import ai.agentscentral.core.agent.instructions.Instructor;
import ai.agentscentral.core.conversation.message.AssistantMessage;
import ai.agentscentral.core.conversation.message.DeveloperMessage;
import ai.agentscentral.core.conversation.message.Message;
import ai.agentscentral.core.conversation.message.TextPart;
import ai.agentscentral.core.factory.AgentJFactory;
import ai.agentscentral.core.handoff.Handoff;
import ai.agentscentral.core.tool.ToolCall;
import ai.agentscentral.openai.client.OpenAIClient;
import ai.agentscentral.openai.client.request.CompletionRequest;
import ai.agentscentral.openai.client.request.parameters.OpenAITool;
import ai.agentscentral.openai.client.response.CompletionResponse;
import ai.agentscentral.openai.client.response.parameters.Choice;
import ai.agentscentral.openai.config.OpenAIConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static ai.agentscentral.core.conversation.message.MessagePartType.text;
import static ai.agentscentral.openai.processor.OpenAIToolConvertor.handOffsToOpenAITools;
import static ai.agentscentral.openai.processor.OpenAIToolConvertor.toolsToOpenAITools;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;

/**
 * OpenAISimpleAgentExecutor
 *
 * @author Rizwan Idrees
 */
public class OpenAISimpleAgentExecutor implements AgentExecutor {

    private static final AgentJFactory agentJFactory = AgentJFactory.getInstance();

    private final SimpleAgent agent;
    private final Map<String, OpenAITool> openAITools = new HashMap<>();
    private final String modelName;
    private final OpenAIConfig config;
    private final OpenAIClient client;
    private final MessageConvertor messageConvert;

    public OpenAISimpleAgentExecutor(SimpleAgent agent, OpenAIClient client) {
        this.agent = agent;
        this.modelName = agent.model().getModel();
        this.config = agent.model().getConfig() instanceof OpenAIConfig c ? c : null;
        this.client = client;
        final Map<String, ToolCall> tools = agentJFactory.getToolBagToolsExtractor().extractTools(agent.toolBags());
        final Map<String, Handoff> handOffs = agentJFactory.getHandoffsExtractor().extractHandOffs(agent.handoffs());
        final Map<String, OpenAITool> mappedTools = toolsToOpenAITools(tools);
        final Map<String, OpenAITool> mappedHandOffs = handOffsToOpenAITools(handOffs);

        ofNullable(mappedTools).ifPresent(openAITools::putAll);
        ofNullable(mappedHandOffs).ifPresent(openAITools::putAll);

        this.messageConvert = new MessageConvertor(tools, handOffs);
    }

    public List<AssistantMessage> process(String contextId, String user, List<Message> messages) {


        final DeveloperMessage agentMessage = new DeveloperMessage(contextId, "",
                new TextPart[]{new TextPart(text, agentInstructions(agent))}, System.currentTimeMillis());

        final CompletionRequest request = CompletionRequest.from(modelName, config, null,
                List.copyOf(openAITools.values()), messageConvert.toOpenAIMessages(agentMessage, messages));

        final CompletionResponse completed = client.complete(request);

        final String completionId = completed.id();
        final Long createdAt = completed.created();

        return completed.choices().stream()
                .map(Choice::message)
                .map(cm -> messageConvert.toAssistantMessage(contextId, completionId, cm, createdAt))
                .toList();
    }

    private String agentInstructions(SimpleAgent agent) {
        if (Objects.isNull(agent.instructors())) {
            return "You are an assistant";
        }

        return agent.instructors().stream().map(Instructor::instruct).collect(joining());
    }

    @Override
    public Agent getAgent() {
        return this.agent;
    }
}
