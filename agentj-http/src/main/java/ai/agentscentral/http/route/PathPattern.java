package ai.agentscentral.http.route;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Compiled path-matching pattern and the ordered list of variable names extracted from
 * a route path template such as {@code /users/{id}/orders/{orderId}}.
 *
 * <p>Created by {@link PathPatternExtractor#extract(String)}.  Used by
 * {@link ControllerMappedRoute} and {@link HttpHandlerRoute} to match incoming request
 * URIs and extract path variable values.</p>
 *
 * @param pattern           regex compiled from the route template, where each
 *                          {@code {variable}} placeholder is replaced by {@code ([^/]+)}
 * @param pathVariableNames ordered list of variable names in declaration order; used
 *                          to correlate capture groups to parameter names
 */
record PathPattern(Pattern pattern, List<String> pathVariableNames) {
}
