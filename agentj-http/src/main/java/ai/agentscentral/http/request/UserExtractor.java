package ai.agentscentral.http.request;

import ai.agentscentral.core.session.user.User;
import jakarta.servlet.http.HttpServletRequest;

public interface UserExtractor {

    User extract(HttpServletRequest request);
}
