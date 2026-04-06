package ai.agentscentral.http.config;

import ai.agentscentral.http.auth.Authorizer;
import ai.agentscentral.http.config.cors.CORSConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * HttpConfig
 *
 * @param authorizers
 * @param corsConfig
 * @author Rizwan Idrees
 */
public record HttpConfig(List<Authorizer> authorizers,
                         CORSConfig corsConfig,
                         ObjectMapper objectMapper) {

}
