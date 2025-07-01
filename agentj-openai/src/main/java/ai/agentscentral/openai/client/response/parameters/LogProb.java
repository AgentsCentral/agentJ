package ai.agentscentral.openai.client.response.parameters;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * LogProb
 *
 * @param bytes
 * @param logprob
 * @param token
 * @param top_logprobs
 * @author Rizwan Idrees
 */
public record LogProb(byte[] bytes,
                      Integer logprob,
                      String token,
                      @JsonProperty("top_logprobs") List<LogProb> top_logprobs) {
}
