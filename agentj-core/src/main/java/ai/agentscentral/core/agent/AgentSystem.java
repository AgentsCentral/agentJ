package ai.agentscentral.core.agent;


import java.util.List;

/**
 * AgentSystem
 *
 * @param name
 * @param defaultAgent
 * @param otherAgents
 * @author Rizwan Idrees
 */
public record AgentSystem(String name, Agent defaultAgent, List<Agent> otherAgents) {
}
