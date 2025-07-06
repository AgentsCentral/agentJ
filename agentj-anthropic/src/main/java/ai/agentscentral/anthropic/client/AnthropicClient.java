package ai.agentscentral.anthropic.client;

import ai.agentscentral.anthropic.client.request.MessagesRequest;
import ai.agentscentral.anthropic.client.response.MessagesResponse;
import ai.agentscentral.anthropic.config.AnthropicConfig;
import ai.agentscentral.core.model.ProviderClient;
import org.apache.hc.client5.http.HttpResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static ai.agentscentral.anthropic.client.Jsonify.asJson;
import static org.apache.hc.client5.http.fluent.Request.post;
import static org.apache.hc.core5.http.ContentType.APPLICATION_JSON;
import static org.apache.hc.core5.http.HttpHeaders.CONTENT_TYPE;

/**
 * AnthropicClient
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

    public AnthropicClient(AnthropicConfig config) {
        this(config.getUrl(), config.getApiKey(), config.getAnthropicVersion());
    }

    public AnthropicClient(String url, String apiKey, String anthropicVersion) {
        this.url = url;
        this.apiKey = apiKey;
        this.anthropicVersion = anthropicVersion;
    }

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
