package ai.agentscentral.http.auth;

import jakarta.servlet.http.HttpServletRequest;

import static java.util.Optional.ofNullable;

/**
 * APIKeyAuthorizer
 *
 * @author Rizwan Idrees
 */
public class APIKeyAuthorizer implements Authorizer {

    private static final String DEFAULT_API_KEY_HEADER = "X-API-Key";

    private final String apiKeyHeaderName;
    private final String apiKeyValue;

    public APIKeyAuthorizer(String apiKeyValue) {
        this(DEFAULT_API_KEY_HEADER, apiKeyValue);
    }

    public APIKeyAuthorizer(String apiKeyHeaderName, String apiKeyValue) {
        this.apiKeyHeaderName = apiKeyHeaderName;
        this.apiKeyValue = apiKeyValue;
    }


    @Override
    public boolean isAuthorized(HttpServletRequest request) {
        return ofNullable(request.getHeader(apiKeyHeaderName))
                .map(apiKeyValue::equals).orElse(false);
    }
}
