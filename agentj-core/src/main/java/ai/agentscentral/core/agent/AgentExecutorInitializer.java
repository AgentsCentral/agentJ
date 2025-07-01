package ai.agentscentral.core.agent;

import ai.agentscentral.core.model.Model;
import ai.agentscentral.core.model.ModelConfig;
import jakarta.annotation.Nonnull;

import java.util.Optional;

/**
 *
 * @author Rizwan Idrees
 */
public class AgentExecutorInitializer {

    public AgentExecutor initialize(@Nonnull Agent agent) {

        if (agent instanceof SimpleAgent simpleAgent) {
            return Optional.of(simpleAgent)
                    .map(SimpleAgent::model)
                    .map(Model::getConfig)
                    .map(ModelConfig::getFactory)
                    .map(f -> f.createAgentExecutor(simpleAgent))
                    .orElseThrow(() -> new RuntimeException("Unable to process type agent type " + agent.getClass()));
        }

        throw new UnsupportedOperationException("Unable to process type agent type " + agent.getClass());
    }

}
