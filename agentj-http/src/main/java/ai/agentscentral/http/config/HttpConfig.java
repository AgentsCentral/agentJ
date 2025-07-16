package ai.agentscentral.http.config;

import ai.agentscentral.core.agentic.Agentic;

/**
 * HttpConfig
 *
 * @param path
 * @param agentic
 * @author Rizwan Idrees
 */
public record HttpConfig(String path, Agentic agentic) {
}
