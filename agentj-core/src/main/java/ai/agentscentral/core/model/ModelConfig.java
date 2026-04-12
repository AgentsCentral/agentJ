package ai.agentscentral.core.model;

import ai.agentscentral.core.agent.Agent;
import ai.agentscentral.core.handoff.Handoff;
import ai.agentscentral.core.provider.ProviderAgentExecutor;
import ai.agentscentral.core.tool.ToolCall;

import java.util.Map;

/**
 * Provider-specific configuration for an LLM model, acting as a factory for the
 * corresponding {@link ProviderAgentExecutor}.
 *
 * <p>Each provider module (e.g. {@code agentj-anthropic}, {@code agentj-openai})
 * supplies its own implementation of this interface — for example
 * {@code AnthropicConfig} or {@code OpenAIConfig} — containing the API key, endpoint,
 * and any other credentials or settings required to call that provider's API.</p>
 *
 * <p>{@code ModelConfig} is referenced by {@link ai.agentscentral.core.model.Model} and
 * called once during agent initialisation by
 * {@link ai.agentscentral.core.agentic.executor.DefaultAgentExecutor} to obtain the
 * executor that will handle all subsequent LLM requests for the agent.</p>
 *
 * @author Rizwan Idrees
 */
public interface ModelConfig {

    /**
     * Creates a {@link ProviderAgentExecutor} wired with the agent's tool and handoff
     * metadata, ready to send requests to the LLM provider.
     *
     * @param agent    the agent for which the executor is being created; provides
     *                 instructors and model name
     * @param tools    map from tool name to {@link ToolCall} descriptor, used to build
     *                 the tool schema included in provider requests
     * @param handOffs map from handoff id to {@link Handoff} descriptor, used to build
     *                 the handoff schema included in provider requests
     * @return a fully configured {@link ProviderAgentExecutor} for this agent
     */
    ProviderAgentExecutor createAgentExecutor(Agent agent, Map<String, ToolCall> tools, Map<String, Handoff> handOffs);
}
