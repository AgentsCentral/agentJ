package ai.agentscentral.anthropic.client.request;

import ai.agentscentral.anthropic.client.request.attributes.AnthropicMessage;
import ai.agentscentral.anthropic.client.request.attributes.AnthropicTool;
import ai.agentscentral.anthropic.client.request.attributes.ServiceTier;
import ai.agentscentral.anthropic.client.request.attributes.SystemPrompt;
import ai.agentscentral.anthropic.client.response.attributes.MetaData;
import ai.agentscentral.anthropic.config.AnthropicConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.List;
import java.util.Set;

import static java.util.Optional.ofNullable;

/**
 * Wire-format record for the Anthropic Messages API request body.
 *
 * <p>Serialised directly to JSON via Jackson; {@code null} fields are omitted thanks to
 * {@link ai.agentscentral.anthropic.client.Jsonify}'s NON_NULL configuration.
 * The {@code system} field is {@link com.fasterxml.jackson.annotation.JsonUnwrapped} so
 * its properties are inlined rather than nested.</p>
 *
 * <p>Use the static factory {@link #from} to construct instances from an
 * {@link AnthropicConfig}.</p>
 *
 * @param model         the Claude model identifier (e.g. {@code "claude-opus-4-6"})
 * @param messages      the ordered conversation history to send
 * @param maxTokens     maximum number of tokens to generate; mapped to {@code max_tokens}
 * @param metaData      optional user metadata (e.g. end-user identifier); mapped to
 *                      {@code metadata}
 * @param serviceTier   optional service tier preference; mapped to {@code service_tier}
 * @param stopSequences optional set of sequences that stop generation early; mapped to
 *                      {@code stop_sequences}
 * @param stream        whether to stream the response; currently must be {@code false}
 *                      for AgentJ's synchronous client
 * @param system        the system prompt, either a plain string or a list of typed
 *                      prompts; unwrapped into the JSON root
 * @param temperature   sampling temperature; {@code null} defers to API default
 * @param tools         the list of available tools and handoff pseudo-tools to expose to
 *                      the model
 * @param topK          top-K sampling parameter; mapped to {@code top_k}
 * @param topP          nucleus sampling parameter; mapped to {@code top_p}
 *
 * @author Rizwan Idrees
 */
public record MessagesRequest(String model,
                              List<AnthropicMessage> messages,
                              @JsonProperty("max_tokens") Integer maxTokens,
                              @JsonProperty("metadata") MetaData metaData,
                              @JsonProperty("service_tier") ServiceTier serviceTier,
                              @JsonProperty("stop_sequences") Set<String> stopSequences,
                              boolean stream,
                              @JsonUnwrapped SystemPrompt system,
                              Double temperature,
                              List<AnthropicTool> tools,
                              @JsonProperty("top_k") Integer topK,
                              @JsonProperty("top_p") Double topP) {

    /**
     * Factory method that assembles a {@code MessagesRequest} from an
     * {@link AnthropicConfig} and the per-call arguments.
     *
     * @param model    the model identifier
     * @param config   the config providing inference parameters
     * @param system   the resolved system prompt
     * @param userId   optional end-user identifier; wrapped in {@link MetaData} if present
     * @param tools    the tools to expose to the model
     * @param messages the conversation history
     * @return a fully populated {@code MessagesRequest}
     */
    public static MessagesRequest from(String model,
                                       AnthropicConfig config,
                                       SystemPrompt system,
                                       String userId,
                                       List<AnthropicTool> tools,
                                       List<AnthropicMessage> messages) {

        return new MessagesRequest(model,
                messages,
                config.getMaxTokens(),
                ofNullable(userId).map(MetaData::new).orElse(null),
                config.getServiceTier(),
                config.getStopSequences(),
                config.isStream(),
                system,
                config.getTemperature(),
                tools,
                config.getTopK(),
                config.getTopP()
        );
    }

}
