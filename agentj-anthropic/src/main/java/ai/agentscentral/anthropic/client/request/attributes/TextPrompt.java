package ai.agentscentral.anthropic.client.request.attributes;

/**
 * A single typed text block within a {@link SystemPrompts} system prompt.
 *
 * <p>The {@link #type()} override always returns {@link #TYPE} ({@code "text"}),
 * ignoring the value supplied to the constructor.  Each
 * {@link ai.agentscentral.core.agent.instructor.Instructor} output is wrapped in one
 * {@code TextPrompt}.</p>
 *
 * @param type the type discriminator; always overridden to {@link #TYPE}
 * @param text the instruction text contributed by an instructor
 *
 * @author Rizwan Idrees
 */
public record TextPrompt(String type, String text) {

    public static final String TYPE = "text";

    public String type() {
        return TYPE;
    }

}
