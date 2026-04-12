package ai.agentscentral.anthropic.executor;

import ai.agentscentral.anthropic.client.AnthropicClient;
import ai.agentscentral.anthropic.client.request.MessagesRequest;
import ai.agentscentral.anthropic.client.request.attributes.*;
import ai.agentscentral.anthropic.client.response.MessagesResponse;
import ai.agentscentral.anthropic.config.AnthropicConfig;
import ai.agentscentral.core.agent.Agent;
import ai.agentscentral.core.agent.instructor.Instructor;
import ai.agentscentral.core.handoff.Handoff;
import ai.agentscentral.core.provider.ProviderAgentExecutor;
import ai.agentscentral.core.session.message.AssistantMessage;
import ai.agentscentral.core.session.message.Message;
import ai.agentscentral.core.session.user.User;
import ai.agentscentral.core.tool.ToolCall;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ai.agentscentral.anthropic.client.request.attributes.TextPrompt.TYPE;
import static ai.agentscentral.anthropic.executor.AnthropicToolConvertor.handOffsToAnthropicTool;
import static ai.agentscentral.anthropic.executor.AnthropicToolConvertor.toolsToAnthropicTool;
import static java.util.Optional.ofNullable;

/**
 * {@link ProviderAgentExecutor} implementation for the Anthropic Claude API.
 *
 * <p>On construction, all AgentJ {@link ToolCall}s and {@link Handoff}s are converted
 * once into {@link AnthropicTool} descriptors and cached.  On each call to
 * {@link #execute}, the agent's instructors are evaluated to form the system prompt,
 * the conversation messages are converted to Anthropic's wire format, and the assembled
 * {@link ai.agentscentral.anthropic.client.request.MessagesRequest} is sent via
 * {@link AnthropicClient}.  The response content is mapped back to AgentJ
 * {@link AssistantMessage}s.</p>
 *
 * <p>Instances are created by {@link AnthropicConfig#createAgentExecutor}.</p>
 *
 * @author Rizwan Idrees
 */
public class AnthropicAgentExecutor implements ProviderAgentExecutor {

    private final Agent agent;
    private final Map<String, AnthropicTool> anthropicTools = new HashMap<>();
    private final String modelName;
    private final AnthropicConfig config;
    private final AnthropicClient client;
    private final MessageConvertor messageConvert;

    /**
     * Creates a new {@code AnthropicAgentExecutor}, converting all tools and handoffs to
     * Anthropic format eagerly.
     *
     * @param agent    the agent whose model, instructors, tools, and handoffs are used
     * @param tools    AgentJ tool definitions keyed by tool name; may be empty
     * @param handOffs AgentJ handoff definitions keyed by handoff id; may be empty
     * @param client   the shared {@link AnthropicClient} used to call the API
     */
    public AnthropicAgentExecutor(Agent agent,
                                  Map<String, ToolCall> tools,
                                  Map<String, Handoff> handOffs,
                                  AnthropicClient client) {
        this.agent = agent;
        this.modelName = agent.model().name();
        this.config = agent.model().config() instanceof AnthropicConfig c ? c : null;
        this.client = client;

        final Map<String, AnthropicTool> mappedTools = toolsToAnthropicTool(tools);
        final Map<String, AnthropicTool> mappedHandOffs = handOffsToAnthropicTool(handOffs);

        ofNullable(mappedTools).ifPresent(anthropicTools::putAll);
        ofNullable(mappedHandOffs).ifPresent(anthropicTools::putAll);

        this.messageConvert = new MessageConvertor(tools, handOffs);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Builds the system prompt from the agent's instructors, constructs a
     * {@link ai.agentscentral.anthropic.client.request.MessagesRequest}, sends it via the
     * {@link AnthropicClient}, and maps each response content block to an
     * {@link AssistantMessage}.</p>
     */
    public List<AssistantMessage> execute(String contextId, User user, List<Message> messages) {


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
        if (agent.instructors().isEmpty()) {
            return new TextSystemPrompt("You are a helpful assistant");
        }

        final List<TextPrompt> textPrompts = agent.instructors().stream()
                .map(Instructor::instruct)
                .map(s -> new TextPrompt(TYPE, s))
                .toList();

        return new SystemPrompts(textPrompts);
    }

}
