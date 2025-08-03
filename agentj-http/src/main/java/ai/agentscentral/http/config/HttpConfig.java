package ai.agentscentral.http.config;

import ai.agentscentral.core.agentic.Agentic;
import ai.agentscentral.http.auth.Authorizer;

import java.util.List;

/**
 * HttpConfig
 *
 * @param path
 * @param agentic
 * @param authorizers
 * @author Rizwan Idrees
 */
public record HttpConfig(String path, Agentic agentic, List<Authorizer> authorizers) {
}
