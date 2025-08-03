package ai.agentscentral.http.request;

import ai.agentscentral.core.session.user.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface UserExtractor {

    Optional<? extends User> extract(HttpServletRequest request);
}
