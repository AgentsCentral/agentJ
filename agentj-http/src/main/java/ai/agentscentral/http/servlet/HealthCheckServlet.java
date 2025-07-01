package ai.agentscentral.http.servlet;

import ai.agentscentral.http.health.LivenessProbe;
import ai.agentscentral.http.health.ReadinessProbe;
import ai.agentscentral.http.health.response.HealthCheckResponse;
import ai.agentscentral.http.health.response.LivenessResponse;
import ai.agentscentral.http.health.response.ReadinessResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.IntStream;

import static ai.agentscentral.http.json.Jsonify.asJson;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;
import static org.apache.commons.lang3.StringUtils.endsWithIgnoreCase;

/**
 * HealthCheckServlet
 *
 * @author Rizwan Idrees
 */
public class HealthCheckServlet extends HttpServlet {

    public static final String CONTENT_TYPE = "application/json";
    private final LivenessProbe livenessProbe;
    private final ReadinessProbe readinessProbe;

    public HealthCheckServlet(LivenessProbe livenessProbe, ReadinessProbe readinessProbe) {
        this.livenessProbe = livenessProbe;
        this.readinessProbe = readinessProbe;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {


        if (endsWithIgnoreCase(request.getRequestURI(), "/liveness")) {
            doLivenessCheck(response);
        } else if (endsWithIgnoreCase(request.getRequestURI(), "/readiness")) {
            doReadinessCheck(response);
        }

        doHealthCheck(response);
    }

    void doHealthCheck(HttpServletResponse response) throws IOException {

        final LivenessResponse liveness = livenessProbe.probe();
        final ReadinessResponse readiness = readinessProbe.probe();

        final int status = IntStream.of(liveness.getHttpStatus(), readiness.getHttpStatus()).filter(s -> s != SC_OK)
                .findFirst().orElse(SC_OK);

        final HealthCheckResponse healthCheckResponse = new HealthCheckResponse(liveness.getBody(),
                readiness.getBody());

        response.setContentType(CONTENT_TYPE);
        response.setStatus(status);

        try (PrintWriter writer = response.getWriter()) {
            writer.println(asJson(healthCheckResponse));
        }
    }

    void doLivenessCheck(HttpServletResponse response) throws IOException {

        final LivenessResponse liveness = livenessProbe.probe();

        response.setContentType(CONTENT_TYPE);
        response.setStatus(liveness.getHttpStatus());

        try (PrintWriter writer = response.getWriter()) {
            writer.println(asJson(liveness.getBody()));
        }
    }

    void doReadinessCheck(HttpServletResponse response) throws IOException {

        final ReadinessResponse readinessResponse = readinessProbe.probe();

        response.setContentType(CONTENT_TYPE);
        response.setStatus(readinessResponse.getHttpStatus());

        try (PrintWriter writer = response.getWriter()) {
            writer.println(asJson(readinessResponse.getBody()));
        }
    }

}
