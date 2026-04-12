package ai.agentscentral.http.health;

import ai.agentscentral.http.request.Request;

/**
 * Functional interface for a health or readiness probe.
 *
 * <p>Implementations perform an application-specific check (e.g. verifying a database
 * connection) and return a {@link ProbeResponse} containing the HTTP status and any
 * additional diagnostic data.  Probes are wrapped in a {@link ai.agentscentral.http.handler.ProbeHandler}
 * and registered as GET routes via {@link ai.agentscentral.http.config.AgentJConfig}.</p>
 *
 * @author Rizwan Idrees
 */
@FunctionalInterface
public interface Probe {

    /**
     * Executes the probe check and returns the result.
     *
     * @param request the incoming HTTP request (e.g. for reading query parameters)
     * @return a {@link ProbeResponse} with an HTTP status code and optional data map
     */
    ProbeResponse probe(Request request);

}
