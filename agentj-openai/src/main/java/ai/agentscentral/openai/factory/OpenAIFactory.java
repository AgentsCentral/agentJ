package ai.agentscentral.openai.factory;

import ai.agentscentral.core.agent.SimpleAgent;
import ai.agentscentral.core.agent.Agent;
import ai.agentscentral.core.model.ProviderFactory;
import ai.agentscentral.openai.client.OpenAIClient;
import ai.agentscentral.openai.processor.OpenAISimpleAgentExecutor;

import java.util.Optional;

/**
 * OpenAIFactory
 *
 * @author Rizwan Idrees
 */
public class OpenAIFactory implements ProviderFactory<OpenAISimpleAgentExecutor, OpenAIClient> {

    private final OpenAIClient client;

    public OpenAIFactory(OpenAIClient client) {
        this.client = client;
    }


    @Override
    public OpenAISimpleAgentExecutor createAgentExecutor(Agent agent) {
        final SimpleAgent simpleAgent = agent instanceof SimpleAgent a ? a : null;
        return Optional.ofNullable(simpleAgent)
                .map(a -> new OpenAISimpleAgentExecutor(a, client))
                .orElseThrow(() -> new RuntimeException("Cannot process agent of type " + agent.getClass()));
    }

}
