package ai.agentscentral.http.request;

import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class SessionIdHeaderExtractor implements SessionIdExtractor {

    private static final String SESSION_ID_HEADER_NAME = "X-Session-Id";

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
