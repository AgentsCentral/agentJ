package ai.agentscentral.anthropic.factory;

import ai.agentscentral.anthropic.client.AnthropicClient;
import ai.agentscentral.anthropic.processor.AnthropicAgentExecutor;
import ai.agentscentral.core.agent.Agent;
import ai.agentscentral.core.factory.AgentJFactory;
import ai.agentscentral.core.model.ProviderFactory;
import jakarta.annotation.Nonnull;

import java.util.Optional;

/**
 * AnthropicFactory
 *
 * @author Rizwan Idrees
 */
public class AnthropicFactory implements ProviderFactory<AnthropicAgentExecutor> {

    private final AnthropicClient client;


    public AnthropicFactory(AnthropicClient client) {
        this.client = client;
    }


    // @Override
    public AnthropicAgentExecutor createAgentExecutor(@Nonnull Agent agent, @Nonnull AgentJFactory agentJFactory) {
        return Optional.of(agent)
                .map(a -> new AnthropicAgentExecutor(a, agentJFactory, client))
                .orElseThrow(() -> new RuntimeException("Cannot process agent of type " + agent.getClass()));
    }

}
