package ai.agentscentral.http.request.convertors;

import ai.agentscentral.core.convertors.Convertor;
import ai.agentscentral.core.convertors.Throwing;
import ai.agentscentral.http.request.Request;
import ai.agentscentral.http.route.HttpMethod;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;

/**
 * Pre-built {@link Convertor} constants for adapting Jakarta Servlet types to AgentJ
 * HTTP types.
 *
 * <p>This is a utility class and cannot be instantiated.</p>
 *
 * @author Rizwan Idrees
 */
public class RequestConvertors {

    private RequestConvertors() {
    }

    /**
     * Converts an {@link HttpServletRequest} to an AgentJ {@link Request}, reading all
     * headers into a case-preserving map and wrapping the body input stream for lazy
     * buffering.
     */
    public static Convertor<HttpServletRequest, Request> servletRequestToRequest = servletRequest -> new Request(
            HttpMethod.valueOf(servletRequest.getMethod()),
            servletRequest.getServletPath(),
            Optional.of(servletRequest)
                    .map(Throwing.<HttpServletRequest, InputStream>lift(ServletRequest::getInputStream))
                    .orElse(InputStream.nullInputStream()),
            servletRequest.getContentLengthLong(),
            servletRequest.getRequestURI(),
            servletRequest.getParameterMap(),
            getHeadersMap(servletRequest)
    );

    private static Map<String, List<String>> getHeadersMap(HttpServletRequest request) {
        return Collections.list(request.getHeaderNames())
                .stream()
                .collect(toMap(
                        headerName -> headerName,
                        headerName -> Collections.list(request.getHeaders(headerName))
                ));
    }

}
