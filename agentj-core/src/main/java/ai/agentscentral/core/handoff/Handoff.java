package ai.agentscentral.core.handoff;

/**
 * Handoff
 *
 * @param id
 * @param agentName
 * @param description
 * @param handoff
 *
 * @author Rizwan Idrees
 */
public record Handoff(String id, String agentName, String description, HandoffFunction handoff) {
}
