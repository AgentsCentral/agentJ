package ai.agentscentral.openai.client.request;

import ai.agentscentral.openai.client.request.parameters.*;
import ai.agentscentral.openai.config.OpenAIConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.annotation.Nonnull;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;

/**
 * CompletionRequest
 *
 * @param messages
 * @param model
 * @param frequencyPenalty
 * @param logitBias
 * @param logProbs
 * @param maxCompletionTokens
 * @param modalities
 * @param n
 * @param parallelToolCalls
 * @param presencePenalty
 * @param responseFormat
 * @param seed
 * @param serviceTier
 * @param stop
 * @param store
 * @param stream
 * @param streamOptions
 * @param temperature
 * @param toolChoice
 * @param tools
 * @param topLogprobs
 * @param topP
 * @param user
 * @param webSearchOptions
 *
 * @author Rizwan Idrees
 */
public record CompletionRequest(List<OpenAIMessage> messages,
                                @Nonnull String model,
                                @JsonProperty("frequency_penalty") Double frequencyPenalty,
                                @JsonProperty("logit_bias") Map<Integer, Integer> logitBias,
                                @JsonProperty("logprobs") Boolean logProbs,
                                @JsonProperty("max_completion_tokens") Integer maxCompletionTokens,
                                List<String> modalities,
                                Integer n,
                                @JsonProperty("parallel_tool_calls") Boolean parallelToolCalls,
                                @JsonProperty("presence_penalty") Double presencePenalty,
                                @JsonProperty("response_format") ResponseFormat responseFormat,
                                Integer seed,
                                @JsonProperty("service_tier") String serviceTier,
                                List<String> stop,
                                Boolean store,
                                Boolean stream,
                                @JsonProperty("stream_options") StreamOptions streamOptions,
                                Double temperature,
                                @JsonProperty("tool_choice") @JsonUnwrapped ToolChoice toolChoice,
                                List<OpenAITool> tools,
                                @JsonProperty("top_logprobs") Integer topLogprobs,
                                @JsonProperty("top_p") Double topP,
                                String user,
                                @JsonProperty("web_search_options") WebSearchOptions webSearchOptions

) {

    public static CompletionRequest from(String model,
                                         OpenAIConfig config,
                                         String user,
                                         List<OpenAITool> tools,
                                         List<OpenAIMessage> messages){
        return new CompletionRequest(
                messages,
                model,
                config.getFrequencyPenalty(),
                config.getLogitBias(),
                config.getLogprobs(),
                config.getMaxCompletionTokens(),
                config.getModalities(),
                config.getN(),
                config.isParallelToolCalls(),
                config.getPresencePenalty(),
                config.getResponseFormat(),
                config.getSeed(),
                config.getServiceTier(),
                config.getStop(),
                config.getStore(),
                config.getStream(),
                nonNull(config.getStream()) && config.getStream()? new StreamOptions(config.isIncludeUsage()) : null,
                config.getTemperature(),
                config.getToolChoice(),
                tools,
                config.getTopLogprobs(),
                config.getTopP(),
                user,
                config.getWebSearchOptions()
        );
    }

}
