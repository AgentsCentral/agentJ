package ai.agentscentral.http.request;

import ai.agentscentral.core.session.user.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

/**
 * Extracts an optional {@link User} identity from an incoming
 * {@link HttpServletRequest}.
 *
 * <p>Implementations may read a session attribute, a JWT claim, or any other
 * request-level source to resolve the calling user.  Returning
 * {@link java.util.Optional#empty()} indicates an anonymous or unauthenticated
 * request.</p>
 *
 * @author Rizwan Idrees
 */
public interface UserExtractor {

    /**
     * Attempts to extract a {@link User} from the given servlet request.
     *
     * @param request the incoming HTTP servlet request
     * @return an {@link java.util.Optional} containing the resolved user, or empty if
     *         the user cannot be determined
     */
    Optional<? extends User> extract(HttpServletRequest request);
}
