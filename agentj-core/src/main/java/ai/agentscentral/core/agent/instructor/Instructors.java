package ai.agentscentral.core.agent.instructor;

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
