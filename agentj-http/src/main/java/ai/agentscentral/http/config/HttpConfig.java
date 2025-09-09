package ai.agentscentral.http.config;

import ai.agentscentral.core.agentic.Agentic;
import ai.agentscentral.http.auth.Authorizer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * HttpConfig
 *
 * @param path
 * @param agentic
 * @param authorizers
 * @author Rizwan Idrees
 */
public record HttpConfig(String path, Agentic agentic, List<Authorizer> authorizers, ObjectMapper objectMapper) {
    public HttpConfig(String path, Agentic agentic, List<Authorizer> authorizers) {
        this(path, agentic, authorizers, new ObjectMapper());
    }
}
