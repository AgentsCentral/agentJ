package ai.agentscentral.openai.factory;

import ai.agentscentral.core.agent.Agent;
import ai.agentscentral.core.factory.AgentJFactory;
import ai.agentscentral.core.handoff.Handoff;
import ai.agentscentral.core.model.ProviderFactory;
import ai.agentscentral.core.tool.ToolCall;
import ai.agentscentral.openai.client.OpenAIClient;
import ai.agentscentral.openai.processor.OpenAIAgentExecutor;
import jakarta.annotation.Nonnull;

import java.util.Map;
import java.util.Optional;

/**
 * OpenAIFactory
 *
 * @author Rizwan Idrees
 */
public class OpenAIFactory implements ProviderFactory<OpenAIAgentExecutor> {

    private final OpenAIClient client;

    public OpenAIFactory(OpenAIClient client) {
        this.client = client;
    }


    @Override
    public OpenAIAgentExecutor createAgentExecutor(@Nonnull Agent agent,
                                                   Map<String, ToolCall> tools,
                                                   Map<String, Handoff> handOffs) {
        return Optional.of(agent)
                .map(a -> new OpenAIAgentExecutor(a, tools, handOffs, client))
                .orElseThrow(() -> new RuntimeException("Cannot process agent of type " + agent.getClass()));
    }

}
