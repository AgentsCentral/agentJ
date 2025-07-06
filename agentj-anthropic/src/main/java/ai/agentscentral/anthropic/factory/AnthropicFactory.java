package ai.agentscentral.anthropic.factory;

import ai.agentscentral.anthropic.client.AnthropicClient;
import ai.agentscentral.anthropic.processor.AnthropicSimpleAgentExecutor;
import ai.agentscentral.core.agent.Agent;
import ai.agentscentral.core.agent.SimpleAgent;
import ai.agentscentral.core.model.ProviderFactory;

import java.util.Optional;

/**
 * AnthropicFactory
 *
 * @author Rizwan Idrees
 */
public class AnthropicFactory implements ProviderFactory<AnthropicSimpleAgentExecutor, AnthropicClient> {

    private final AnthropicClient client;

    public AnthropicFactory(AnthropicClient client) {
        this.client = client;
    }


    @Override
    public AnthropicSimpleAgentExecutor createAgentExecutor(Agent agent) {
        final SimpleAgent simpleAgent = agent instanceof SimpleAgent a ? a : null;
        return Optional.ofNullable(simpleAgent)
                .map(a -> new AnthropicSimpleAgentExecutor(a, client))
                .orElseThrow(() -> new RuntimeException("Cannot process agent of type " + agent.getClass()));
    }

}
