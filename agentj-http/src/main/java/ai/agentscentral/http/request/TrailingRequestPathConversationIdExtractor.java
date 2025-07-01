package ai.agentscentral.http.request;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.*;

/**
 * TrailingRequestPathConversationIdExtractor
 *
 * @author Rizwan Idrees
 */
public class TrailingRequestPathConversationIdExtractor implements ConversationIdExtractor {

    private static final String WILDCARD = "*";
    private static final String SLASH = "/";
    private final String path;

    public TrailingRequestPathConversationIdExtractor(String path) {
        this.path = cleanPath(path);
    }

    @Override
    public Optional<String> extract(HttpServletRequest request) {
        return extractConversationId(request.getRequestURI());
    }

    private Optional<String> extractConversationId(String requestUri) {
        final String uri = endsWith(requestUri, SLASH)? requestUri : requestUri + SLASH;
        return Optional.ofNullable(uri)
                .map(this::removePath)
                .map(this::removeSlash)
                .filter(StringUtils::isNotBlank);
    }

    private String cleanPath(String path) {
        final String pathWithoutWildCard = replaceEach(path, new String[]{WILDCARD}, new String[]{StringUtils.EMPTY});
        return pathWithoutWildCard.endsWith(SLASH) ? pathWithoutWildCard : pathWithoutWildCard + SLASH;
    }

    private String removePath(String uriPath) {
        return replaceEach(uriPath, new String[]{path}, new String[]{EMPTY});
    }

    private String removeSlash(String uriPath) {
        return replaceEach(uriPath, new String[]{SLASH}, new String[]{EMPTY});
    }
}
