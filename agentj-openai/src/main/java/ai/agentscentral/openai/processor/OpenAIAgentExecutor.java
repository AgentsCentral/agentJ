package ai.agentscentral.openai.processor;

import ai.agentscentral.core.agent.Agent;
import ai.agentscentral.core.agent.instructor.Instructor;
import ai.agentscentral.core.factory.AgentJFactory;
import ai.agentscentral.core.handoff.Handoff;
import ai.agentscentral.core.provider.ProviderAgentExecutor;
import ai.agentscentral.core.session.message.AssistantMessage;
import ai.agentscentral.core.session.message.DeveloperMessage;
import ai.agentscentral.core.session.message.Message;
import ai.agentscentral.core.session.message.TextPart;
import ai.agentscentral.core.session.user.User;
import ai.agentscentral.core.tool.ToolCall;
import ai.agentscentral.openai.client.OpenAIClient;
import ai.agentscentral.openai.client.request.CompletionRequest;
import ai.agentscentral.openai.client.request.attributes.OpenAITool;
import ai.agentscentral.openai.client.response.CompletionResponse;
import ai.agentscentral.openai.client.response.parameters.Choice;
import ai.agentscentral.openai.client.response.parameters.ChoiceMessage;
import ai.agentscentral.openai.config.OpenAIConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ai.agentscentral.core.session.message.MessagePartType.text;
import static ai.agentscentral.openai.processor.OpenAIToolConvertor.handOffsToOpenAITools;
import static ai.agentscentral.openai.processor.OpenAIToolConvertor.toolsToOpenAITools;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;

/**
 * OpenAISimpleAgentExecutor
 *
 * @author Rizwan Idrees
 */
public class OpenAIAgentExecutor implements ProviderAgentExecutor {


    private final Agent agent;
    private final Map<String, OpenAITool> openAITools = new HashMap<>();
    private final String modelName;
    private final OpenAIConfig config;
    private final OpenAIClient client;
    private final MessageConvertor messageConvertor;

    public OpenAIAgentExecutor(Agent agent, AgentJFactory agentJFactory, OpenAIClient client) {
        this.agent = agent;
        this.modelName = agent.model().name();
        this.config = agent.model().config() instanceof OpenAIConfig c ? c : null;
        this.client = client;
        final Map<String, ToolCall> tools = agentJFactory.getToolBagToolsExtractor().extractTools(agent.toolBags());
        final Map<String, Handoff> handOffs = agentJFactory.getHandoffsExtractor().extractHandOffs(agent.handoffs());
        final Map<String, OpenAITool> mappedTools = toolsToOpenAITools(tools);
        final Map<String, OpenAITool> mappedHandOffs = handOffsToOpenAITools(handOffs);

        ofNullable(mappedTools).ifPresent(openAITools::putAll);
        ofNullable(mappedHandOffs).ifPresent(openAITools::putAll);

        this.messageConvertor = new MessageConvertor(tools, handOffs);
    }

    public List<AssistantMessage> execute(String contextId, User user, List<Message> messages) {

        final DeveloperMessage agentMessage = new DeveloperMessage(contextId, "",
                new TextPart[]{new TextPart(text, agentInstructions())}, System.currentTimeMillis());

        final CompletionRequest request = CompletionRequest.from(modelName, config, user.id(),
                List.copyOf(openAITools.values()), messageConvertor.toOpenAIMessages(agentMessage, messages));

        final CompletionResponse completed = client.complete(request);

        final String completionId = completed.id();
        final Long createdAt = completed.created();

        final List<ChoiceMessage> choiceMessages = completed.choices().stream()
                .map(Choice::message)
                .toList();

        return List.of(messageConvertor.toAssistantMessage(contextId, completionId, choiceMessages, createdAt));
    }

    private String agentInstructions() {
        if (agent.instructors().isEmpty()) {
            return "You are a helpful assistant";
        }

        return agent.instructors().stream().map(Instructor::instruct).collect(joining());
    }

    @Override
    public Agent getAgent() {
        return this.agent;
    }
}
