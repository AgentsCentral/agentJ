package ai.agentscentral.anthropic.processor;

import ai.agentscentral.anthropic.client.AnthropicClient;
import ai.agentscentral.anthropic.client.request.attributes.AnthropicTool;
import ai.agentscentral.anthropic.config.AnthropicConfig;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static ai.agentscentral.core.conversation.message.MessagePartType.text;
import static java.util.stream.Collectors.joining;

/**
 * AnthropicSimpleAgentExecutor
 *
 * @author Rizwan Idrees
 */
public class AnthropicSimpleAgentExecutor implements AgentExecutor {

    private static final AgentJFactory agentJFactory = AgentJFactory.getInstance();

    private final SimpleAgent agent;
    private final Map<String, AnthropicTool> anthropicTools = new HashMap<>();
    private final String modelName;
    private final AnthropicConfig config;
    private final AnthropicClient client;
   // private final MessageConvertor messageConvert;

    public AnthropicSimpleAgentExecutor(SimpleAgent agent, AnthropicClient client) {
        this.agent = agent;
        this.modelName = agent.model().getModel();
        this.config = agent.model().getConfig() instanceof AnthropicConfig c ? c : null;
        this.client = client;
        final Map<String, ToolCall> tools = agentJFactory.getToolBagToolsExtractor().extractTools(agent.toolBags());
        final Map<String, Handoff> handOffs = agentJFactory.getHandoffsExtractor().extractHandOffs(agent.handoffs());
        //final Map<String, AnthropicTool> mappedTools = toolsToOpenAITools(tools);
        //   final Map<String, AnthropicTool> mappedHandOffs = handOffsToOpenAITools(handOffs);

        //   ofNullable(mappedTools).ifPresent(openAITools::putAll);
        //   ofNullable(mappedHandOffs).ifPresent(openAITools::putAll);

        //   this.messageConvert = new MessageConvertor(tools, handOffs);
    }

    public List<AssistantMessage> process(String contextId, String user, List<Message> messages) {


        final DeveloperMessage agentMessage = new DeveloperMessage(contextId, "",
                new TextPart[]{new TextPart(text, agentInstructions(agent))}, System.currentTimeMillis());

//        final MessagesRequest request = MessagesRequest.from(modelName, config, null,
//                List.copyOf(openAITools.values()), messageConvert.toOpenAIMessages(agentMessage, messages));
//
//        final CompletionResponse completed = client.complete(request);
//
//        final String completionId = completed.id();
//        final Long createdAt = completed.created();
//
//        return completed.choices().stream()
//                .map(Choice::message)
//                .map(cm -> messageConvert.toAssistantMessage(contextId, completionId, cm, createdAt))
//                .toList();
        return null;
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
