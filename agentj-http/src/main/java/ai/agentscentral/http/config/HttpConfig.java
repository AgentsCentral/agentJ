package ai.agentscentral.http.config;

import ai.agentscentral.core.agent.AgentSystem;

/**
 * HttpConfig
 *
 * @param path
 * @param agentSystem
 * @author Rizwan Idrees
 */
public record HttpConfig(String path, AgentSystem agentSystem) {
}
