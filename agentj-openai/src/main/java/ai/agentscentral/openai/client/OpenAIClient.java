package ai.agentscentral.openai.client;

import ai.agentscentral.core.model.ProviderClient;
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
 * OpenAIClient
 *
 * @author Rizwan Idrees
 */
public class OpenAIClient implements ProviderClient {

    public static final String BEARER = "Bearer ";
    private final String url;
    private final String authorization;

    public OpenAIClient(String url, String apiKey) {
        this.url = url;
        this.authorization = BEARER + apiKey;
    }

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


