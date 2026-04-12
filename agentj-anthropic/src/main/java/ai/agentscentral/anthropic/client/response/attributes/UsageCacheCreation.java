package ai.agentscentral.anthropic.client.response.attributes;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Detailed breakdown of tokens written to Anthropic's ephemeral prompt cache, split
 * by TTL bucket.
 *
 * @param ephemeral1hInputTokens tokens written to the 1-hour ephemeral cache bucket;
 *                               mapped from {@code ephemeral_1h_input_tokens}
 * @param ephemeral5mInputTokens tokens written to the 5-minute ephemeral cache bucket;
 *                               mapped from {@code ephemeral_5m_input_tokens}
 *
 * @author Rizwan Idrees
 */
public record UsageCacheCreation(@JsonProperty("ephemeral_1h_input_tokens") Integer ephemeral1hInputTokens,
                                 @JsonProperty("ephemeral_5m_input_tokens") Integer ephemeral5mInputTokens) {
}
