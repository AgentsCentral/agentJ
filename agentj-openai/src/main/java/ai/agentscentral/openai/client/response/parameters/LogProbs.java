package ai.agentscentral.openai.client.response.parameters;

/**
 * Log-probability container returned within a {@link Choice} when {@code logprobs} is
 * requested.
 *
 * @param content per-token log-probabilities for the generated content tokens
 * @param refusal per-token log-probabilities for refusal text tokens; {@code null} when
 *                no refusal was generated
 *
 * @author Rizwan Idrees
 */
public record LogProbs(LogProb content, LogProb refusal) {
}
