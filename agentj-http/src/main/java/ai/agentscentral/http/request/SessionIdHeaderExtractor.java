package ai.agentscentral.http.request;

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * {@link SessionIdExtractor} that reads the session identifier from the
 * {@code X-Session-Id} request header (case-insensitive).
 *
 * <p>Returns {@link java.util.Optional#empty()} if the header is absent or blank.</p>
 *
 * @author Rizwan Idrees
 */
public class SessionIdHeaderExtractor implements SessionIdExtractor {

    private static final String SESSION_ID_HEADER_NAME = "X-Session-Id";

    /**
     * Creates a new {@code SessionIdHeaderExtractor}.
     */
    public SessionIdHeaderExtractor() {
    }

    @Override
    public Optional<String> extract(Request request) {
        return request.headers().entrySet()
                .stream()
                .filter(e -> SESSION_ID_HEADER_NAME.equalsIgnoreCase(e.getKey()))
                .map(Map.Entry::getValue)
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .filter(StringUtils::isNotBlank)
                .findFirst();
    }
}
