package ai.agentscentral.openai.factory;

import ai.agentscentral.core.agent.Agent;
import ai.agentscentral.core.agentic.Agentic;
import ai.agentscentral.core.model.ProviderFactory;
import ai.agentscentral.openai.client.OpenAIClient;
import ai.agentscentral.openai.processor.OpenAIAgentExecutor;

import java.util.Optional;

/**
 * OpenAIFactory
 *
 * @author Rizwan Idrees
 */
public class OpenAIFactory implements ProviderFactory<OpenAIAgentExecutor, OpenAIClient> {

    private final OpenAIClient client;

    public OpenAIFactory(OpenAIClient client) {
        this.client = client;
    }


    @Override
    public OpenAIAgentExecutor createAgentExecutor(Agentic agentic) {
        final Agent simpleAgent = agentic instanceof Agent a ? a : null;
        return Optional.ofNullable(simpleAgent)
                .map(a -> new OpenAIAgentExecutor(a, client))
                .orElseThrow(() -> new RuntimeException("Cannot process agent of type " + agentic.getClass()));
    }

}
