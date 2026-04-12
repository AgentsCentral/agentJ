package ai.agentscentral.openai.client.request.attributes;

/**
 * Function definition within an {@link OpenAITool} describing the tool to the model.
 *
 * @param name        unique function name the model will use when calling this tool
 * @param description human-readable description helping the model decide when to call
 *                    the function
 * @param parameters  JSON-schema-style parameter definition; {@code null} for parameter-
 *                    less functions (e.g. handoffs)
 * @param strict      when {@code true}, enables strict schema enforcement for structured
 *                    outputs
 *
 * @author Rizwan Idrees
 */
public record ToolFunction(String name,
                           String description,
                           FunctionParameters parameters,
                           Boolean strict) {

}
