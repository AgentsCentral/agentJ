package ai.agentscentral.core.agentic.executor;

import ai.agentscentral.core.agent.Agent;
import ai.agentscentral.core.agentic.Agentic;
import ai.agentscentral.core.model.Model;
import ai.agentscentral.core.model.ModelConfig;
import ai.agentscentral.core.team.Team;
import jakarta.annotation.Nonnull;

import java.util.Optional;

/**
 * @author Rizwan Idrees
 */
public class AgentExecutorInitializer {

    public AgenticExecutor initialize(@Nonnull Agentic agentic) {

        if (agentic instanceof Agent simpleAgent) {
            return Optional.of(simpleAgent)
                    .map(Agent::model)
                    .map(Model::getConfig)
                    .map(ModelConfig::getFactory)
                    .map(f -> f.createAgentExecutor(simpleAgent))
                    .orElseThrow(() -> new RuntimeException("Unable to process type agent type " + agentic.getClass()));
        } else if (agentic instanceof Team team) {

        }

        throw new UnsupportedOperationException("Unable to process type agent type " + agentic.getClass());
    }

}
