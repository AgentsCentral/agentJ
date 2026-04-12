package ai.agentscentral.http.auth;

import ai.agentscentral.http.request.Request;

import static java.util.Optional.ofNullable;

/**
 * {@link Authorizer} that validates requests by comparing a request header value
 * against a pre-configured API key.
 *
 * <p>By default the header name is {@code X-API-Key}; a custom header name can be
 * provided via the two-argument constructor.  The authorizer returns {@code true} only
 * when the header is present and its value exactly matches the configured key.</p>
 *
 * @author Rizwan Idrees
 */
public class APIKeyAuthorizer implements Authorizer {

    private static final String DEFAULT_API_KEY_HEADER = "X-API-Key";

    private final String apiKeyHeaderName;
    private final String apiKeyValue;

    /**
     * Creates an {@code APIKeyAuthorizer} that reads from the default
     * {@code X-API-Key} header.
     *
     * @param apiKeyValue the expected API key value
     */
    public APIKeyAuthorizer(String apiKeyValue) {
        this(DEFAULT_API_KEY_HEADER, apiKeyValue);
    }

    /**
     * Creates an {@code APIKeyAuthorizer} with a custom header name.
     *
     * @param apiKeyHeaderName the name of the HTTP header carrying the API key
     * @param apiKeyValue      the expected API key value
     */
    public APIKeyAuthorizer(String apiKeyHeaderName, String apiKeyValue) {
        this.apiKeyHeaderName = apiKeyHeaderName;
        this.apiKeyValue = apiKeyValue;
    }


    @Override
    public boolean isAuthorized(Request request) {
        return ofNullable(request.getHeader(apiKeyHeaderName))
                .map(apiKeyValue::equals).orElse(false);
    }
}
