package ai.agentscentral.anthropic.client;

import ai.agentscentral.anthropic.client.request.MessagesRequest;
import ai.agentscentral.anthropic.client.response.MessagesResponse;
import ai.agentscentral.anthropic.config.AnthropicConfig;
import ai.agentscentral.core.provider.ProviderClient;
import org.apache.hc.client5.http.HttpResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static ai.agentscentral.anthropic.client.Jsonify.asJson;
import static org.apache.hc.client5.http.fluent.Request.post;
import static org.apache.hc.core5.http.ContentType.APPLICATION_JSON;
import static org.apache.hc.core5.http.HttpHeaders.CONTENT_TYPE;

/**
 * Low-level HTTP client for the Anthropic Messages API.
 *
 * <p>Sends a {@link MessagesRequest} to the configured endpoint using Apache HttpComponents
 * Fluent API and deserialises the response into a {@link MessagesResponse} via
 * {@link Jsonify}.  Authentication is provided by the {@code x-api-key} header; the
 * API version is sent as {@code anthropic-version}.</p>
 *
 * <p>Can be constructed from an {@link AnthropicConfig} instance or from raw credential
 * strings.  A single instance is created per {@link AnthropicConfig} and shared across all
 * agents that use that configuration.</p>
 *
 * @author Rizwan Idrees
 */
public class AnthropicClient implements ProviderClient {

    private static final Logger logger = LoggerFactory.getLogger(AnthropicClient.class);

    public static final String HEADER_ANTHROPIC_VERSION = "anthropic-version";
    public static final String HEADER_X_API_KEY = "x-api-key";
    private final String url;
    private final String apiKey;
    private final String anthropicVersion;

    /**
     * Creates an {@code AnthropicClient} from an {@link AnthropicConfig}.
     *
     * @param config the Anthropic configuration providing URL, API key, and version
     */
    public AnthropicClient(AnthropicConfig config) {
        this(config.getUrl(), config.getApiKey(), config.getAnthropicVersion());
    }

    /**
     * Creates an {@code AnthropicClient} with explicit credential values.
     *
     * @param url               the full URL of the Anthropic Messages endpoint
     * @param apiKey            the Anthropic API key sent as {@code x-api-key}
     * @param anthropicVersion  the API version string sent as {@code anthropic-version}
     */
    public AnthropicClient(String url, String apiKey, String anthropicVersion) {
        this.url = url;
        this.apiKey = apiKey;
        this.anthropicVersion = anthropicVersion;
    }

    /**
     * Sends a {@link MessagesRequest} to the Anthropic Messages endpoint and returns the
     * parsed response.
     *
     * @param request the fully assembled request to send
     * @return the deserialized {@link MessagesResponse}
     * @throws RuntimeException wrapping any {@link java.io.IOException} or
     *                          {@link org.apache.hc.client5.http.HttpResponseException}
     *                          that occurs during the HTTP call
     */
    public MessagesResponse sendMessages(MessagesRequest request) {

        try {

            final String responseAsString = post(url)
                    .addHeader(HEADER_X_API_KEY, apiKey)
                    .addHeader(HEADER_ANTHROPIC_VERSION, anthropicVersion)
                    .addHeader(CONTENT_TYPE, APPLICATION_JSON.getMimeType())
                    .bodyString(asJson(request), APPLICATION_JSON)
                    .execute().returnContent().asString();


            return Jsonify.asObject(responseAsString, MessagesResponse.class);

        } catch (HttpResponseException e) {
            logger.error("Http error while processing. error {} content {}", e.getMessage(),
                    new String(e.getContentBytes()), e);
            throw new RuntimeException(e);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

}
