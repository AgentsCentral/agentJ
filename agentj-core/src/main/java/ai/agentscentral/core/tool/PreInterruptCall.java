package ai.agentscentral.core.tool;

import ai.agentscentral.core.session.user.User;

@FunctionalInterface
public interface PreInterruptCall {

    void call(User user);

}
