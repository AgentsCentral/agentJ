package ai.agentscentral.core.model;

import ai.agentscentral.core.agent.Agent;
import ai.agentscentral.core.handoff.Handoff;
import ai.agentscentral.core.provider.ProviderAgentExecutor;
import ai.agentscentral.core.tool.ToolCall;
import jakarta.annotation.Nonnull;

import java.util.Map;

/**
 * ProviderFactory
 *
 * @param <P>
 * @author Rizwan Idrees
 */
public interface ProviderFactory<P extends ProviderAgentExecutor> {

    P createAgentExecutor(@Nonnull Agent agent, Map<String, ToolCall> tools, Map<String, Handoff> handOffs);

}
