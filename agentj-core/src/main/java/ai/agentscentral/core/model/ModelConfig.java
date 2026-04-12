package ai.agentscentral.core.model;

import ai.agentscentral.core.agent.Agent;
import ai.agentscentral.core.handoff.Handoff;
import ai.agentscentral.core.provider.ProviderAgentExecutor;
import ai.agentscentral.core.tool.ToolCall;

import java.util.Map;

/**
 * ModelConfig
 *
 * @author Rizwan Idrees
 */
public interface ModelConfig {

    ProviderAgentExecutor createAgentExecutor(Agent agent, Map<String, ToolCall> tools, Map<String, Handoff> handOffs);
}
