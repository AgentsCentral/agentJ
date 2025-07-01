package ai.agentscentral.openai.client.response.parameters;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * PromptTokenDetails
 *
 * @param audioTokens
 * @param cachedTokens
 * @author Rizwan Idrees
 */
public record PromptTokenDetails(@JsonProperty("audio_tokens") Integer audioTokens,
                                 @JsonProperty("cached_tokens") Integer cachedTokens) {
}
