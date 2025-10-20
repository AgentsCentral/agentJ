package ai.agentscentral.core.tool;

import ai.agentscentral.core.session.user.User;

/**
 * PreInterruptCall
 *
 * @author Rizwan Idrees
 */
@FunctionalInterface
public interface PreInterruptCall {

    void call(User user);

}
