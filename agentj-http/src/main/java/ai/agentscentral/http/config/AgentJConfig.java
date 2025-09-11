package ai.agentscentral.http.config;

import ai.agentscentral.http.config.cors.CORSConfig;

import java.util.List;

/**
 * AgentJConfig
 *
 * @param httpConfigs
 *
 * @author Rizwan Idrees
 */
public record AgentJConfig(List<HttpConfig> httpConfigs) {

}
