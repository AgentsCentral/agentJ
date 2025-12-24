package ai.agentscentral.http.route;

import java.util.List;
import java.util.regex.Pattern;

/**
 * PathPattern
 *
 * @param pattern
 * @param pathVariableNames
 */
record PathPattern(Pattern pattern, List<String> pathVariableNames) {
}
