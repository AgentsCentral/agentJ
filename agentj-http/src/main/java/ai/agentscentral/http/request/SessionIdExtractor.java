package ai.agentscentral.http.request;

import java.util.Optional;

/**
 * Functional interface for extracting an existing session identifier from an HTTP
 * request.
 *
 * <p>Implementations inspect a specific part of the request (e.g. a header or a
 * trailing path segment) for a session ID.  If none is found they return
 * {@link java.util.Optional#empty()}, signalling the handler to generate a new ID via
 * the configured {@link ai.agentscentral.core.session.id.SessionIdGenerator}.</p>
 *
 * <p>Built-in implementations:
 * <ul>
 *   <li>{@link SessionIdHeaderExtractor} — reads the {@code X-Session-Id} header</li>
 *   <li>{@link SessionIdTrailingPathExtractor} — extracts the ID from the trailing
 *       segment of the request URI</li>
 * </ul>
 *
 * @author Rizwan Idrees
 */
@FunctionalInterface
public interface SessionIdExtractor {

    /**
     * Attempts to extract a session identifier from the given request.
     *
     * @param request the incoming HTTP request
     * @return an {@link java.util.Optional} containing the session ID, or empty if none
     *         is present
     */
    Optional<String> extract(Request request);

}
