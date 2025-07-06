package ai.agentscentral.anthropic.client.response.attributes;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Usage
 *
 * @param inputTokens
 * @param outputTokens
 * @param cacheCreationInputTokens
 * @param cacheReadInputTokens
 * @param cacheCreation
 * @param serviceTier
 * @author Rizwan Idrees
 */
public record Usage(@JsonProperty("input_tokens") Integer inputTokens,
                    @JsonProperty("output_tokens") Integer outputTokens,
                    @JsonProperty("cache_creation_input_tokens") Integer cacheCreationInputTokens,
                    @JsonProperty("cache_read_input_tokens") Integer cacheReadInputTokens,
                    @JsonProperty("cache_creation") UsageCacheCreation cacheCreation,
                    @JsonProperty("service_tier") UsageServiceTier serviceTier) {
}
