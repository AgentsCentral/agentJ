package ai.agentscentral.anthropic.client.response.attributes;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * UsageCacheCreation
 *
 * @param ephemeral1hInputTokens
 * @param ephemeral5mInputTokens
 * @author Rizwan Idrees
 */
public record UsageCacheCreation(@JsonProperty("ephemeral_1h_input_tokens") Integer ephemeral1hInputTokens,
                                 @JsonProperty("ephemeral_5m_input_tokens") Integer ephemeral5mInputTokens) {
}
