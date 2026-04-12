package ai.agentscentral.anthropic.config;

import ai.agentscentral.anthropic.client.AnthropicClient;
import ai.agentscentral.anthropic.client.request.attributes.ServiceTier;
import ai.agentscentral.anthropic.executor.AnthropicAgentExecutor;
import ai.agentscentral.core.agent.Agent;
import ai.agentscentral.core.handoff.Handoff;
import ai.agentscentral.core.model.ModelConfig;
import ai.agentscentral.core.provider.ProviderAgentExecutor;
import ai.agentscentral.core.tool.ToolCall;
import jakarta.annotation.Nonnull;

import java.util.Map;
import java.util.Set;

/**
 * AnthropicConfig
 *
 * @author Rizwan Idrees
 */
public class AnthropicConfig implements ModelConfig {

    public static final String DEFAULT_URL = "https://api.anthropic.com/v1/messages";
    private final AnthropicClient client;
    private String url;
    private String apiKey;
    private String anthropicVersion;
    private Integer maxTokens;
    private ServiceTier serviceTier;
    private Set<String> stopSequences;
    private boolean stream;
    private Double temperature = 1.0D;
    private Integer topK;
    private Double topP;


    public AnthropicConfig(@Nonnull String apiKey,
                           @Nonnull String anthropicVersion,
                           @Nonnull Integer maxTokens) {
        this(DEFAULT_URL, apiKey, anthropicVersion, maxTokens);
    }

    public AnthropicConfig(@Nonnull String url,
                           @Nonnull String apiKey,
                           @Nonnull String anthropicVersion,
                           @Nonnull Integer maxTokens) {
        this.url = url;
        this.apiKey = apiKey;
        this.anthropicVersion = anthropicVersion;
        this.maxTokens = maxTokens;
        this.client = new AnthropicClient(url, apiKey, anthropicVersion);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getAnthropicVersion() {
        return anthropicVersion;
    }

    public void setAnthropicVersion(String anthropicVersion) {
        this.anthropicVersion = anthropicVersion;
    }

    public Integer getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(Integer maxTokens) {
        this.maxTokens = maxTokens;
    }

    public ServiceTier getServiceTier() {
        return serviceTier;
    }

    public void setServiceTier(ServiceTier serviceTier) {
        this.serviceTier = serviceTier;
    }

    public Set<String> getStopSequences() {
        return stopSequences;
    }

    public void setStopSequences(Set<String> stopSequences) {
        this.stopSequences = stopSequences;
    }

    public boolean isStream() {
        return stream;
    }

    public void setStream(boolean stream) {
        this.stream = stream;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Integer getTopK() {
        return topK;
    }

    public void setTopK(Integer topK) {
        this.topK = topK;
    }

    public Double getTopP() {
        return topP;
    }

    public void setTopP(Double topP) {
        this.topP = topP;
    }

    @Override
    public ProviderAgentExecutor createAgentExecutor(Agent agent,
                                                     Map<String, ToolCall> tools,
                                                     Map<String, Handoff> handOffs) {
        return new AnthropicAgentExecutor(agent, tools, handOffs, this.client);
    }
}
