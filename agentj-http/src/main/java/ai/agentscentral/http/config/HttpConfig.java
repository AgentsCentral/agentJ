package ai.agentscentral.http.config;

import ai.agentscentral.http.auth.Authorizer;
import ai.agentscentral.http.config.cors.CORSConfig;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * Top-level HTTP infrastructure configuration for an AgentJ server.
 *
 * <p>Aggregates the cross-cutting concerns of the HTTP layer:
 * <ul>
 *   <li>An ordered list of {@link Authorizer}s applied to every incoming request by
 *       the authorization filter</li>
 *   <li>A {@link CORSConfig} controlling cross-origin access policies</li>
 *   <li>A shared {@link ObjectMapper} used for JSON serialisation and deserialisation
 *       throughout the HTTP pipeline</li>
 * </ul>
 *
 * @param authorizers  the list of {@link Authorizer} instances evaluated in order; a
 *                     request is rejected as {@code 401 Unauthorized} if any authorizer
 *                     returns {@code false}; may be empty for unauthenticated endpoints
 * @param corsConfig   the CORS policy applied by {@link ai.agentscentral.http.filter.CORSFilter};
 *                     may be {@code null} to disable CORS handling
 * @param objectMapper the Jackson {@link ObjectMapper} used for request/response
 *                     conversion
 *
 * @author Rizwan Idrees
 */
public record HttpConfig(List<Authorizer> authorizers,
                         CORSConfig corsConfig,
                         ObjectMapper objectMapper) {

}
