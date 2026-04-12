package ai.agentscentral.openai.client.request;

import ai.agentscentral.openai.client.request.attributes.*;
import ai.agentscentral.openai.config.OpenAIConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;

/**
 * Wire record for the OpenAI {@code POST /v1/chat/completions} request body.
 *
 * <p>All parameters map 1:1 to the OpenAI API documentation.  Use the
 * {@link #from(String, OpenAIConfig, String, List, List)} factory to construct from an
 * {@link OpenAIConfig}.</p>
 *
 * @param messages            the ordered list of messages comprising the conversation
 * @param model               the model ID to use (e.g. {@code "gpt-4o"})
 * @param frequencyPenalty    {@code frequency_penalty} — penalises repeated tokens;
 *                            {@code null} uses the API default
 * @param logitBias           {@code logit_bias} — token bias map; {@code null} if not
 *                            used
 * @param logProbs            {@code logprobs} — whether to return log-probabilities
 * @param maxCompletionTokens {@code max_completion_tokens} — upper limit on generated
 *                            tokens
 * @param modalities          output modality list (e.g. {@code ["text"]})
 * @param n                   number of completion choices to generate
 * @param parallelToolCalls   {@code parallel_tool_calls} — whether to call multiple
 *                            tools in parallel
 * @param presencePenalty     {@code presence_penalty} — penalises tokens already present
 * @param responseFormat      structured output format specification
 * @param seed                deterministic sampling seed
 * @param serviceTier         {@code service_tier} — routing tier hint
 * @param stop                stop sequence(s) at which generation halts
 * @param store               whether to store the completion for future distillation
 * @param stream              whether to stream partial completion deltas
 * @param streamOptions       {@code stream_options} — options for streamed responses
 * @param temperature         sampling temperature ({@code 0}–{@code 2})
 * @param toolChoice          {@code tool_choice} — controls which tool (if any) is called
 * @param tools               list of tools available to the model
 * @param topLogprobs         {@code top_logprobs} — number of most-likely tokens to
 *                            return per position
 * @param topP                {@code top_p} — nucleus sampling probability mass
 * @param user                end-user identifier for abuse detection
 * @param webSearchOptions    {@code web_search_options} — web search grounding options
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

    /**
     * Creates a {@code CompletionRequest} from an {@link OpenAIConfig}, merging the
     * runtime-provided model, user, tools, and messages with the config's optional
     * request parameters.
     *
     * @param model    the model ID to use
     * @param config   provider config supplying all optional parameters
     * @param user     end-user identifier; may be {@code null}
     * @param tools    tools to advertise to the model; may be empty
     * @param messages the full message history for this turn
     * @return an assembled {@code CompletionRequest}
     */
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
