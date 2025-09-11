package ai.agentscentral.http.config;

import ai.agentscentral.core.agentic.Agentic;
import ai.agentscentral.http.auth.Authorizer;
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
 * @author Mustafa Bhuiyan
 */
public record HttpConfig(String path, Agentic agentic, List<Authorizer> authorizers, CORSConfig corsConfig) {
}
