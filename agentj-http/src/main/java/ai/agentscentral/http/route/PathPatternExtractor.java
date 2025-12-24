package ai.agentscentral.http.route;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static org.apache.commons.lang3.Strings.CS;

/**
 * PathPatternExtractor
 *
 * @author Rizwan Idrees
 */
class PathPatternExtractor {

    private static final String REPLACEMENT = "([^/]+)";
    private static final String $ = "$";
    private static final String PATH_REGEX = "\\{[^/]+\\}";
    private static final String REGEX_PREFIX = "^";
    private static final String OPENING_EXPRESSION = "{";
    private static final String CLOSING_EXPRESSION = "}";


    private PathPatternExtractor() {
    }

    static PathPattern extract(String path) {
        final Pattern pattern = Pattern.compile(REGEX_PREFIX + path.replaceAll(PATH_REGEX, REPLACEMENT) + $);
        return new PathPattern(pattern, extractVariableNames(pattern, path));
    }

    static List<String> extractVariableNames(Pattern pattern, String path) {
        return extractPathVariables(pattern, path).stream().map(PathPatternExtractor::clean).toList();
    }

    static List<String> extractPathVariables(Pattern pattern, String path) {
        final Matcher matcher = pattern.matcher(path);
        if (matcher.find()) {
            return IntStream.range(1, matcher.groupCount() + 1)
                    .mapToObj(matcher::group).toList();
        }
        return List.of();
    }

    private static String clean(String pathWithExpression) {
        return CS.removeEnd(CS.removeStart(pathWithExpression, OPENING_EXPRESSION), CLOSING_EXPRESSION);
    }
}
