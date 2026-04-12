package ai.agentscentral.core.agent.instructor;

/**
 * Utility class providing common {@link Instructor} implementations.
 *
 * <p>This is a utility class and cannot be instantiated.</p>
 *
 * @author Rizwan Idrees
 */
public class Instructors {

    private Instructors() {
    }

    /**
     * Creates an {@link Instructor} that always returns the given string as its instruction.
     *
     * <p>Use this when the agent's system prompt is a fixed, static string known at
     * construction time.</p>
     *
     * @param input the static instruction text to return; should not be {@code null}
     * @return an {@code Instructor} whose {@code instruct()} method always returns {@code input}
     */
    public static Instructor stringInstructor(String input) {
        return () -> input;
    }
}
