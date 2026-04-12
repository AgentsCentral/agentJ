package ai.agentscentral.core.agent.instructor;


/**
 * Functional interface representing a source of system instructions for an
 * {@link ai.agentscentral.core.agent.Agent}.
 *
 * <p>Each {@code Instructor} contributes a segment of the agent's system prompt.
 * An agent can hold multiple instructors; their outputs are combined in order to
 * form the full instruction context sent to the model.</p>
 *
 * <p>Implementations are typically provided as lambdas or method references.
 * For static text, use {@link Instructors#stringInstructor(String)}.</p>
 *
 * @author Rizwan Idrees
 */
@FunctionalInterface
public interface Instructor {

    /**
     * Returns the instruction text contributed by this instructor.
     *
     * @return a non-null string containing the system instruction text
     */
    String instruct();

}
