package ai.agentscentral.openai.client.response.parameters;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Usage
 *
 * @param completionTokens
 * @param promptTokens
 * @param totalTokens
 * @param completionTokensDetails
 * @param promptTokensDetails
 * @author Rizwan Idrees
 */
public record Usage(@JsonProperty("completion_tokens") Integer completionTokens,
                    @JsonProperty("prompt_tokens") Integer promptTokens,
                    @JsonProperty("total_tokens") Integer totalTokens,
                    @JsonProperty("completion_tokens_details") CompletionTokenDetails completionTokensDetails,
                    @JsonProperty("prompt_tokens_details") PromptTokenDetails promptTokensDetails) {
}
