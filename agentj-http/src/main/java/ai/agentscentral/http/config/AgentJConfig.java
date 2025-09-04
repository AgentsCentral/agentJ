package ai.agentscentral.http.config;

import ai.agentscentral.http.config.cors.CORSConfig;

import java.util.List;

/**
 * AgentJConfig
 *
 * @param httpConfigs
 * @param corsConfig
 *
 * @author Rizwan Idrees
 * @author Mustafa Bhuiyan
 */
public record AgentJConfig(List<HttpConfig> httpConfigs, CORSConfig corsConfig) {

}
