package ai.agentscentral.openai.client.response.parameters;

/**
 * LogProbs
 *
 * @param content
 * @param refusal
 * @author Rizwan Idrees
 */
public record LogProbs(LogProb content, LogProb refusal) {
}
