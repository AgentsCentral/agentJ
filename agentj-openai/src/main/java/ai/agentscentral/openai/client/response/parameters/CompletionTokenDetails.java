package ai.agentscentral.openai.client.response.parameters;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * CompletionTokenDetails
 *
 * @param acceptedPredictionTokens
 * @param audioTokens
 * @param reasoningTokens
 * @param rejectedPredictionTokens
 * @author Rizwan Idrees
 */
public record CompletionTokenDetails(@JsonProperty("accepted_prediction_tokens") Integer acceptedPredictionTokens,
                                     @JsonProperty("audio_tokens") Integer audioTokens,
                                     @JsonProperty("reasoning_tokens") Integer reasoningTokens,
                                     @JsonProperty("rejected_prediction_tokens") Integer rejectedPredictionTokens) {
}
