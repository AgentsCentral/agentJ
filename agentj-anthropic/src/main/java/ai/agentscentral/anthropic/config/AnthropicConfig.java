package ai.agentscentral.anthropic.config;

import ai.agentscentral.anthropic.client.AnthropicClient;
import ai.agentscentral.anthropic.client.request.attributes.ServiceTier;
import ai.agentscentral.anthropic.factory.AnthropicFactory;
import ai.agentscentral.core.model.ModelConfig;
import ai.agentscentral.core.model.ProviderFactory;
import ai.agentscentral.core.provider.ProviderAgentExecutor;
import jakarta.annotation.Nonnull;

import java.util.Set;

import static java.util.Optional.ofNullable;

/**
 * AnthropicConfig
 *
 * @author Rizwan Idrees
 */
public class AnthropicConfig implements ModelConfig {

    public static final String DEFAULT_URL = "https://api.anthropic.com/v1/messages";
    private AnthropicFactory factory;

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


    public AnthropicConfig(@Nonnull String apiKey, @Nonnull String anthropicVersion, @Nonnull Integer maxTokens) {
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
    public ProviderFactory<? extends ProviderAgentExecutor> getFactory() {
        factory = ofNullable(factory)
                .orElseGet(() -> new AnthropicFactory(new AnthropicClient(url, apiKey, anthropicVersion)));
        return factory;
    }
}
