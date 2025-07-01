package ai.agentscentral.core.agent.instructions;

/**
 * Instructors
 *
 * @author Rizwan Idrees
 */
public class Instructors {

    private Instructors() {
    }

    public static Instructor stringInstructor(String input) {
        return () -> input;
    }
}
