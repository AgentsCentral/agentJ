package ai.agentscentral.openai.client.response.parameters;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Token usage statistics from a {@link ai.agentscentral.openai.client.response.CompletionResponse}.
 *
 * @param completionTokens       {@code completion_tokens} — tokens in the generated
 *                               completion
 * @param promptTokens           {@code prompt_tokens} — tokens in the input prompt
 * @param totalTokens            {@code total_tokens} — sum of prompt and completion
 *                               tokens
 * @param completionTokensDetails {@code completion_tokens_details} — fine-grained
 *                                breakdown of completion tokens
 * @param promptTokensDetails    {@code prompt_tokens_details} — fine-grained breakdown
 *                               of prompt tokens
 *
 * @author Rizwan Idrees
 */
public record Usage(@JsonProperty("completion_tokens") Integer completionTokens,
                    @JsonProperty("prompt_tokens") Integer promptTokens,
                    @JsonProperty("total_tokens") Integer totalTokens,
                    @JsonProperty("completion_tokens_details") CompletionTokenDetails completionTokensDetails,
                    @JsonProperty("prompt_tokens_details") PromptTokenDetails promptTokensDetails) {
}
