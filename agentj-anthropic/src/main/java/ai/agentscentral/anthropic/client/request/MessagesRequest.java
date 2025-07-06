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
 * MessagesRequest
 *
 * @param model
 * @param messages
 * @param maxTokens
 * @param metaData
 * @param serviceTier
 * @param stopSequences
 * @param stream
 * @param system
 * @param temperature
 * @param tools
 * @param topK
 * @param topP
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
