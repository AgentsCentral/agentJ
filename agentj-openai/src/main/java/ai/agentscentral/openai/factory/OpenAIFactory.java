package ai.agentscentral.openai.factory;

import ai.agentscentral.core.agent.Agent;
import ai.agentscentral.core.factory.AgentJFactory;
import ai.agentscentral.core.model.ProviderFactory;
import ai.agentscentral.openai.client.OpenAIClient;
import ai.agentscentral.openai.processor.OpenAIAgentExecutor;
import jakarta.annotation.Nonnull;

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
    public OpenAIAgentExecutor createAgentExecutor(@Nonnull Agent agent, @Nonnull AgentJFactory agentJFactory) {
        return Optional.of(agent)
                .map(a -> new OpenAIAgentExecutor(a, agentJFactory, client))
                .orElseThrow(() -> new RuntimeException("Cannot process agent of type " + agent.getClass()));
    }

}
