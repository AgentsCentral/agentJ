package ai.agentscentral.http.servlet;

import ai.agentscentral.http.response.Response;
import ai.agentscentral.http.route.HttpRouter;
import ai.agentscentral.http.route.Route;
import ai.agentscentral.http.route.convertors.ContentConvertor;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static ai.agentscentral.core.convertors.Convertor.convert;
import static ai.agentscentral.http.request.convertors.RequestConvertors.servletRequestToRequest;
import static ai.agentscentral.http.route.HttpRouter.NOT_FOUND;

/**
 * AgentJServlet
 *
 * @author Rizwan Idrees
 * @author Mustafa Bhuiyan
 * @author Hasnain Khan
 */
public class AgentJServlet extends HttpServlet {

    private final HttpRouter router;
    private final ContentConvertor convertor;

    public AgentJServlet(List<Route> routes, ContentConvertor convertor) {
        this.router = new HttpRouter(routes);
        this.convertor = convertor;
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse servletResponse) {

        final Response<?> response = Optional.of(request)
                .map(convert(servletRequestToRequest))
                .<Response<?>>map(router::route)
                .orElse(NOT_FOUND);

        final Optional<String> body = Optional.of(response)
                .filter(r -> Objects.nonNull(response.resource()))
                .map(convertor::convert);

        servletResponse.setContentType(response.contentType());
        servletResponse.setStatus(response.status());

        body.ifPresent(b -> writeBody(servletResponse, b));
    }

    private void writeBody(HttpServletResponse servletResponse, String body) {
        try (PrintWriter writer = servletResponse.getWriter()) {
            writer.print(body);
            writer.flush();
        } catch (IOException e) {
            handleIOError(servletResponse);
        }
    }

    private void handleIOError(HttpServletResponse response) {
        if (!response.isCommitted()) {
            response.reset();
            response.setStatus(500);
        }
    }
}
