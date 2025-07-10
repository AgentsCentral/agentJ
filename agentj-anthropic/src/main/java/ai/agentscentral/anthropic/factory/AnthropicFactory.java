package ai.agentscentral.anthropic.factory;

import ai.agentscentral.anthropic.client.AnthropicClient;
import ai.agentscentral.anthropic.processor.AnthropicAgentExecutor;
import ai.agentscentral.core.agent.Agent;
import ai.agentscentral.core.agentic.Agentic;
import ai.agentscentral.core.model.ProviderFactory;

import java.util.Optional;

/**
 * AnthropicFactory
 *
 * @author Rizwan Idrees
 */
public class AnthropicFactory implements ProviderFactory<AnthropicAgentExecutor, AnthropicClient> {

    private final AnthropicClient client;

    public AnthropicFactory(AnthropicClient client) {
        this.client = client;
    }


    @Override
    public AnthropicAgentExecutor createAgentExecutor(Agentic agentic) {
        final Agent simpleAgent = agentic instanceof Agent a ? a : null;
        return Optional.ofNullable(simpleAgent)
                .map(a -> new AnthropicAgentExecutor(a, client))
                .orElseThrow(() -> new RuntimeException("Cannot process agent of type " + agentic.getClass()));
    }

}
