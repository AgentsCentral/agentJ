package ai.agentscentral.openai.client.request.attributes;

/**
 * {@link ToolChoice} implementation that forces the model to call a specific function.
 *
 * <p>The {@code type} component always returns {@code "function"} via the
 * {@link #type()} override.</p>
 *
 * @param type     always {@code "function"}
 * @param function identifies the specific function the model must call
 *
 * @author Rizwan Idrees
 */
public record ToolChoiceFunctionCall(String type, ToolChoiceFunction function) implements ToolChoice {

    private static final String FUNCTION = "function";

    @Override
    public String type() {
        return FUNCTION;
    }
}
