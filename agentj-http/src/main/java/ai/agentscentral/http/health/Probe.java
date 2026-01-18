package ai.agentscentral.http.health;

import ai.agentscentral.http.request.Request;

/**
 * Probe
 *
 * @author Rizwan Idrees
 */
@FunctionalInterface
public interface Probe {

    ProbeResponse probe(Request request);

}
