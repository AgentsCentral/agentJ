package ai.agentscentral.http.config;

import ai.agentscentral.core.agentic.Agentic;
import ai.agentscentral.http.auth.Authorizer;
import com.fasterxml.jackson.databind.ObjectMapper;
import ai.agentscentral.http.config.cors.CORSConfig;

import java.util.List;

/**
 * HttpConfig
 *
 * @param path
 * @param agentic
 * @param authorizers
 * @param corsConfig
 *
 * @author Rizwan Idrees
 */
public record HttpConfig(String path, Agentic agentic, List<Authorizer> authorizers, CORSConfig corsConfig, ObjectMapper objectMapper) {
    public HttpConfig(String path, Agentic agentic, List<Authorizer> authorizers, CORSConfig corsConfig) {
        this(path, agentic, authorizers, corsConfig, new ObjectMapper());
    }
}
