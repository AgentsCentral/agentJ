package ai.agentscentral.openai.client.request.attributes;

/**
 * Wire record describing a tool available to the model in a
 * {@link ai.agentscentral.openai.client.request.CompletionRequest}.
 *
 * <p>The {@code type} component always returns {@value #FUNCTION} via the
 * {@link #type()} override.</p>
 *
 * @param type     always {@value #FUNCTION}
 * @param function the function definition including name, description, and parameters
 *
 * @author Rizwan Idrees
 */
public record OpenAITool(String type, ToolFunction function) {

    /** Tool type discriminator; the only supported value in current API versions. */
    public static final String FUNCTION = "function";

    @Override
    public String type() {
        return FUNCTION;
    }
}
