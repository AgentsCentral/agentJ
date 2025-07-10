package ai.agentscentral.http.config;

import ai.agentscentral.core.team.Team;

/**
 * HttpConfig
 *
 * @param path
 * @param team
 * @author Rizwan Idrees
 */
public record HttpConfig(String path, Team team) {
}
