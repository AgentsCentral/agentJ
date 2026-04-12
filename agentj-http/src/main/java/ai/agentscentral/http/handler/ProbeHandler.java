package ai.agentscentral.http.handler;

import ai.agentscentral.http.health.Probe;
import ai.agentscentral.http.health.ProbeResponse;
import ai.agentscentral.http.request.Request;
import ai.agentscentral.http.response.Response;

/**
 * {@link HttpHandler} that delegates to a {@link Probe} and wraps its
 * {@link ProbeResponse} in an HTTP {@link ai.agentscentral.http.response.Response}.
 *
 * <p>Used by {@link ai.agentscentral.http.config.AgentJConfig} for the default
 * liveness and readiness health-check routes.</p>
 *
 * @author Rizwan Idrees
 */
public class ProbeHandler implements HttpHandler<ProbeResponse> {

    private static final String CONTENT_TYPE = "application/json";

    private final Probe probe;

    /**
     * Creates a {@code ProbeHandler} wrapping the given {@link Probe}.
     *
     * @param probe the probe to invoke on each request
     */
    public ProbeHandler(Probe probe) {
        this.probe = probe;
    }

    @Override
    public Response<ProbeResponse> handle(Request request) {
        final ProbeResponse probeResponse = probe.probe(request);
        return Response.<ProbeResponse>response(probeResponse.status(), CONTENT_TYPE)
                .resource(probeResponse)
                .build();
    }

}
