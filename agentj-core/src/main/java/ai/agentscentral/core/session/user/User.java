package ai.agentscentral.core.session.user;

/**
 * Represents the end-user associated with a conversational session.
 *
 * <p>A {@code User} is passed through the executor chain and made available to tool
 * methods and pre-interrupt calls, allowing agent logic to personalise responses or apply
 * per-user policies. Implementations are supplied by the application and may hold
 * additional attributes beyond the core identity fields.</p>
 *
 * @author Rizwan Idrees
 */
public interface User {

    /**
     * Returns the unique identifier of this user.
     *
     * @return the user id; never {@code null}
     */
    String id();

    /**
     * Returns the display name of this user.
     *
     * @return the user's name; never {@code null}
     */
    String name();

}
