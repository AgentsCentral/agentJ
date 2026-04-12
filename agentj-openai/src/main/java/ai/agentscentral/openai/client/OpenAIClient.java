package ai.agentscentral.openai.client;

import ai.agentscentral.core.provider.ProviderClient;
import ai.agentscentral.openai.client.request.CompletionRequest;
import ai.agentscentral.openai.client.response.CompletionResponse;
import org.apache.hc.client5.http.HttpResponseException;

import java.io.IOException;

import static ai.agentscentral.openai.client.Jsonify.asJson;
import static org.apache.hc.client5.http.fluent.Request.post;
import static org.apache.hc.core5.http.ContentType.APPLICATION_JSON;
import static org.apache.hc.core5.http.HttpHeaders.AUTHORIZATION;
import static org.apache.hc.core5.http.HttpHeaders.CONTENT_TYPE;

/**
 * Low-level HTTP client for the OpenAI Chat Completions API.
 *
 * <p>Serialises a {@link CompletionRequest} to JSON using {@link Jsonify}, POSTs it to
 * the configured endpoint with a {@code Bearer} token, and deserialises the response
 * body back into a {@link CompletionResponse}.</p>
 *
 * @author Rizwan Idrees
 */
public class OpenAIClient implements ProviderClient {

    /** Authorization header prefix for OpenAI API requests. */
    public static final String BEARER = "Bearer ";
    private final String url;
    private final String authorization;

    /**
     * Creates an {@code OpenAIClient} for the given endpoint and API key.
     *
     * @param url    Chat Completions endpoint URL (e.g.
     *               {@code https://api.openai.com/v1/chat/completions})
     * @param apiKey OpenAI API key; combined with {@link #BEARER} for the
     *               {@code Authorization} header
     */
    public OpenAIClient(String url, String apiKey) {
        this.url = url;
        this.authorization = BEARER + apiKey;
    }

    /**
     * Sends a {@link CompletionRequest} to the OpenAI API and returns the parsed
     * {@link CompletionResponse}.
     *
     * @param request the completion request to send
     * @return the API response
     * @throws RuntimeException if the HTTP request fails or the response cannot be parsed
     */
    public CompletionResponse complete(CompletionRequest request) {

        try {
            final String responseAsString = post(url)
                    .addHeader(AUTHORIZATION, authorization)
                    .addHeader(CONTENT_TYPE, APPLICATION_JSON.getMimeType())
                    .bodyString(asJson(request), APPLICATION_JSON)
                    .execute().returnContent().asString();
            return Jsonify.asObject(responseAsString, CompletionResponse.class);

        } catch (HttpResponseException e) {
            e.printStackTrace();
            //todo:: better error handling
            throw new RuntimeException(e);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

}


