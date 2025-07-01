package ai.agentscentral.openai.client.response.parameters;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Choice
 *
 * @param index
 * @param finishReason
 * @param logProbs
 * @param message
 * @author Rizwan Idrees
 */
public record Choice(@JsonProperty("index") Integer index,
                     @JsonProperty("finish_reason") String finishReason,
                     @JsonProperty("logprobs") LogProbs logProbs,
                     ChoiceMessage message
) {
}
