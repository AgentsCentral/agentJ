package ai.agentscentral.http.filter;

import ai.agentscentral.http.config.cors.CORSConfig;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * CORSFilter
 *
 * @author Mustafa Bhuiyan
 */
public class CORSFilter implements Filter {

    private final CORSConfig corsConfig;

    public CORSFilter(CORSConfig corsConfig) {
        this.corsConfig = corsConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest instanceof HttpServletRequest httpRequest && servletResponse instanceof HttpServletResponse httpResponse) {
            String origin = httpRequest.getHeader("Origin");

            if (origin == null || origin.isBlank()) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }

            if (!isAllowedOrigin(origin)) {
                httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Origin not allowed");
                return;
            }

            // Preflight Request
            if (isPreflightRequest(httpRequest)) {
                handlePreflightRequest(httpRequest, httpResponse, origin);
                return; // Short circuit and respond; no need to continue
            }

            // Actual Request
            addCorsHeaders(httpResponse, origin);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void handlePreflightRequest(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String origin) throws IOException {
        String requestMethod = httpRequest.getHeader("Access-Control-Request-Method");
        String requestHeaders = httpRequest.getHeader("Access-Control-Request-Headers");

        // Method Validation
        if (!isAllowedMethod(requestMethod)) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Method not allowed");
            return;
        }

        // Headers Validation
        if (!isAllowedHeaders(requestHeaders)) {
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Headers not allowed");
            return;
        }

        addCorsHeaders(httpResponse, origin);
        httpResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    private void addCorsHeaders(HttpServletResponse httpResponse, String origin) {
        httpResponse.setHeader("Access-Control-Allow-Origin", origin);
        httpResponse.setHeader("Vary", "Origin");
        httpResponse.setHeader("Access-Control-Allow-Methods", String.join(",", corsConfig.allowedMethods()));
        httpResponse.setHeader("Access-Control-Allow-Headers", String.join(",", corsConfig.allowedHeaders()));

        if (corsConfig.allowCredentials()) {
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        }
    }

    private boolean isAllowedOrigin(String origin) {
        return corsConfig.allowedOrigins().contains(origin);
    }

    private boolean isAllowedMethod(String method) {
        if (method == null || method.isBlank()) {
            return false;
        }
        return corsConfig.allowedMethods().contains(method.toUpperCase());
    }

    private boolean isAllowedHeaders(String requestHeaders) {
        if (requestHeaders == null || requestHeaders.isBlank()) {
            return true; // No headers to validate
        }
        List<String> requestHeaderList = Arrays.stream(requestHeaders.split(","))
                .map(String::trim).map(String::toUpperCase).toList();
        Set<String> allowedHeadersSet = corsConfig.allowedHeaders().stream()
                .map(String::trim).map(String::toUpperCase).collect(toSet());
        return allowedHeadersSet.containsAll(requestHeaderList);
    }

    private boolean isPreflightRequest(HttpServletRequest httpRequest) {
        return ("OPTIONS".equals(httpRequest.getMethod())
                && httpRequest.getHeader("Access-Control-Request-Method") != null);
    }
}
