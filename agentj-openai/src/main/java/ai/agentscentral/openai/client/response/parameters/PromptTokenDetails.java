package ai.agentscentral.openai.client.response.parameters;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Fine-grained breakdown of prompt token counts within a {@link Usage} record.
 *
 * @param audioTokens  {@code audio_tokens} — tokens consumed by audio input
 * @param cachedTokens {@code cached_tokens} — tokens served from the prompt cache
 *
 * @author Rizwan Idrees
 */
public record PromptTokenDetails(@JsonProperty("audio_tokens") Integer audioTokens,
                                 @JsonProperty("cached_tokens") Integer cachedTokens) {
}
