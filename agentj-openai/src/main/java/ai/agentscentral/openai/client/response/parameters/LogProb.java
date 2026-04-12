package ai.agentscentral.openai.client.response.parameters;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Log-probability information for a single token position in a {@link LogProbs} object.
 *
 * @param bytes        UTF-8 byte representation of the token; may be {@code null}
 * @param logprob      the log-probability of this token
 * @param token        the string value of the token
 * @param top_logprobs {@code top_logprobs} — the most-likely alternative tokens at this
 *                     position with their own log-probabilities
 *
 * @author Rizwan Idrees
 */
public record LogProb(byte[] bytes,
                      Integer logprob,
                      String token,
                      @JsonProperty("top_logprobs") List<LogProb> top_logprobs) {
}
