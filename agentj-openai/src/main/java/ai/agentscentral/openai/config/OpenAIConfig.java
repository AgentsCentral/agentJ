package ai.agentscentral.openai.config;

import ai.agentscentral.core.model.ModelConfig;
import ai.agentscentral.core.model.ProviderFactory;
import ai.agentscentral.core.provider.ProviderAgentExecutor;
import ai.agentscentral.openai.client.OpenAIClient;
import ai.agentscentral.openai.client.request.attributes.ResponseFormat;
import ai.agentscentral.openai.client.request.attributes.ToolChoice;
import ai.agentscentral.openai.client.request.attributes.WebSearchOptions;
import ai.agentscentral.openai.factory.OpenAIFactory;

import java.util.List;
import java.util.Map;

import static java.util.Optional.ofNullable;

/**
 * OpenAIConfig
 *
 * @author Rizwan Idrees
 */
public class OpenAIConfig implements ModelConfig {

    private OpenAIFactory factory;

    private String url = "https://api.openai.com/v1/chat/completions";
    private String apiKey;
    private Double frequencyPenalty;
    private Map<Integer, Integer> logitBias;
    private Boolean logprobs;
    private Integer maxCompletionTokens;
    private List<String> modalities;
    private Integer n;
    private Boolean parallelToolCalls;
    private Double presencePenalty;
    private ResponseFormat responseFormat;
    private Integer seed;
    private String serviceTier;
    private List<String> stop;
    private Boolean store;
    private Boolean stream;
    private Double temperature;
    private Boolean includeUsage;
    private ToolChoice toolChoice;
    private Integer topLogprobs;
    private Double topP;
    private WebSearchOptions webSearchOptions;

    public OpenAIConfig(String apiKey) {
        this.apiKey = apiKey;
    }

    public OpenAIConfig(Double temperature, String apiKey) {
        this(apiKey);
        this.temperature = temperature;
    }

    public OpenAIConfig(Double temperature, String apiKey, String url) {
        this(temperature, apiKey);
        this.url = url;
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

    public Double getFrequencyPenalty() {
        return frequencyPenalty;
    }

    public void setFrequencyPenalty(Double frequencyPenalty) {
        this.frequencyPenalty = frequencyPenalty;
    }

    public Map<Integer, Integer> getLogitBias() {
        return logitBias;
    }

    public void setLogitBias(Map<Integer, Integer> logitBias) {
        this.logitBias = logitBias;
    }

    public Boolean getLogprobs() {
        return logprobs;
    }

    public void setLogprobs(Boolean logprobs) {
        this.logprobs = logprobs;
    }

    public Integer getMaxCompletionTokens() {
        return maxCompletionTokens;
    }

    public void setMaxCompletionTokens(Integer maxCompletionTokens) {
        this.maxCompletionTokens = maxCompletionTokens;
    }

    public List<String> getModalities() {
        return modalities;
    }

    public void setModalities(List<String> modalities) {
        this.modalities = modalities;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public Boolean isParallelToolCalls() {
        return parallelToolCalls;
    }

    public void setParallelToolCalls(Boolean parallelToolCalls) {
        this.parallelToolCalls = parallelToolCalls;
    }

    public Double getPresencePenalty() {
        return presencePenalty;
    }

    public void setPresencePenalty(Double presencePenalty) {
        this.presencePenalty = presencePenalty;
    }

    public ResponseFormat getResponseFormat() {
        return responseFormat;
    }

    public void setResponseFormat(ResponseFormat responseFormat) {
        this.responseFormat = responseFormat;
    }

    public Integer getSeed() {
        return seed;
    }

    public void setSeed(Integer seed) {
        this.seed = seed;
    }

    public String getServiceTier() {
        return serviceTier;
    }

    public void setServiceTier(String serviceTier) {
        this.serviceTier = serviceTier;
    }

    public List<String> getStop() {
        return stop;
    }

    public void setStop(List<String> stop) {
        this.stop = stop;
    }

    public Boolean getStore() {
        return store;
    }

    public void setStore(Boolean store) {
        this.store = store;
    }

    public Boolean getStream() {
        return stream;
    }

    public void setStream(Boolean stream) {
        this.stream = stream;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Boolean isIncludeUsage() {
        return includeUsage;
    }

    public void setIncludeUsage(Boolean includeUsage) {
        this.includeUsage = includeUsage;
    }

    public ToolChoice getToolChoice() {
        return toolChoice;
    }

    public void setToolChoice(ToolChoice toolChoice) {
        this.toolChoice = toolChoice;
    }

    public Integer getTopLogprobs() {
        return topLogprobs;
    }

    public void setTopLogprobs(Integer topLogprobs) {
        this.topLogprobs = topLogprobs;
    }

    public Double getTopP() {
        return topP;
    }

    public void setTopP(Double topP) {
        this.topP = topP;
    }

    public WebSearchOptions getWebSearchOptions() {
        return webSearchOptions;
    }

    public void setWebSearchOptions(WebSearchOptions webSearchOptions) {
        this.webSearchOptions = webSearchOptions;
    }

    @Override
    public ProviderFactory<? extends ProviderAgentExecutor> getFactory() {
        factory = ofNullable(factory).orElseGet(() -> new OpenAIFactory(new OpenAIClient(this.url, this.apiKey)));
        return factory;
    }
}
