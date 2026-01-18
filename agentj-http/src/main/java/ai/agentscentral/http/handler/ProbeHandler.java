package ai.agentscentral.http.handler;

import ai.agentscentral.http.health.Probe;
import ai.agentscentral.http.health.ProbeResponse;
import ai.agentscentral.http.request.Request;
import ai.agentscentral.http.response.Response;

public class ProbeHandler implements HttpHandler<ProbeResponse> {

    private static final String CONTENT_TYPE = "application/json";

    private final Probe probe;

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
