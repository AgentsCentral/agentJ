package ai.agentscentral.openai.client.request.attributes;

/**
 * OpenAITool
 *
 * @param type
 * @param function
 * @author Rizwan Idrees
 */
public record OpenAITool(String type, ToolFunction function) {

    public static final String FUNCTION = "function";

    @Override
    public String type() {
        return FUNCTION;
    }
}
