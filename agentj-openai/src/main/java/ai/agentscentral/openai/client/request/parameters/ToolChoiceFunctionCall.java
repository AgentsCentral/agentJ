package ai.agentscentral.openai.client.request.parameters;

/**
 * ToolChoiceFunctionCall
 *
 * @param type
 * @param function
 * @author Rizwan Idrees
 */
public record ToolChoiceFunctionCall(String type, ToolChoiceFunction function) implements ToolChoice {

    private static final String FUNCTION = "function";

    @Override
    public String type() {
        return FUNCTION;
    }
}
