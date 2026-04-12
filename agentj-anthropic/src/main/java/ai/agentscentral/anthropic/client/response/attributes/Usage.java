package ai.agentscentral.anthropic.client.response.attributes;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Token usage statistics returned by the Anthropic Messages API for a single request.
 *
 * @param inputTokens              the number of input tokens consumed; mapped from
 *                                 {@code input_tokens}
 * @param outputTokens             the number of output tokens generated; mapped from
 *                                 {@code output_tokens}
 * @param cacheCreationInputTokens input tokens written to the prompt cache during this
 *                                 request; mapped from {@code cache_creation_input_tokens}
 * @param cacheReadInputTokens     input tokens read from the prompt cache; mapped from
 *                                 {@code cache_read_input_tokens}
 * @param cacheCreation            detailed breakdown of ephemeral cache-creation tokens
 *                                 by TTL bucket; mapped from {@code cache_creation}
 * @param serviceTier              the service tier that processed the request; mapped
 *                                 from {@code service_tier}
 * @param inferenceGeo             the geographic region that served the inference;
 *                                 mapped from {@code inference_geo}
 *
 * @author Rizwan Idrees
 */
public record Usage(@JsonProperty("input_tokens") Integer inputTokens,
                    @JsonProperty("output_tokens") Integer outputTokens,
                    @JsonProperty("cache_creation_input_tokens") Integer cacheCreationInputTokens,
                    @JsonProperty("cache_read_input_tokens") Integer cacheReadInputTokens,
                    @JsonProperty("cache_creation") UsageCacheCreation cacheCreation,
                    @JsonProperty("service_tier") UsageServiceTier serviceTier,
                    @JsonProperty("inference_geo") String inferenceGeo) {
}
