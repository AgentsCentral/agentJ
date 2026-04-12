package ai.agentscentral.openai.client.request.attributes;

/**
 * {@link ResponseFormat} that instructs the model to produce plain text output.
 *
 * <p>The {@code type} component always returns {@code "text"} via the {@link #type()}
 * override.</p>
 *
 * @param type always {@code "text"}
 *
 * @author Rizwan Idrees
 */
public record TextResponseFormat(String type) implements ResponseFormat {

    private static final String TEXT = "text";

    @Override
    public String type() {
        return TEXT;
    }

}
