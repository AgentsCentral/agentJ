package ai.agentscentral.openai.client.response.parameters;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Fine-grained breakdown of completion token counts within a {@link Usage} record.
 *
 * @param acceptedPredictionTokens {@code accepted_prediction_tokens} — tokens from
 *                                  accepted speculative predictions
 * @param audioTokens              {@code audio_tokens} — tokens used for audio output
 * @param reasoningTokens          {@code reasoning_tokens} — tokens used for internal
 *                                  chain-of-thought reasoning
 * @param rejectedPredictionTokens {@code rejected_prediction_tokens} — tokens from
 *                                  rejected speculative predictions
 *
 * @author Rizwan Idrees
 */
public record CompletionTokenDetails(@JsonProperty("accepted_prediction_tokens") Integer acceptedPredictionTokens,
                                     @JsonProperty("audio_tokens") Integer audioTokens,
                                     @JsonProperty("reasoning_tokens") Integer reasoningTokens,
                                     @JsonProperty("rejected_prediction_tokens") Integer rejectedPredictionTokens) {
}
