package ai.agentscentral.openai.client.request.parameters;

/**
 * ToolFunction
 *
 * @param name
 * @param description
 * @param parameters
 * @param strict
 * @author Rizwan Idrees
 */
public record ToolFunction(String name,
                           String description,
                           FunctionParameters parameters,
                           Boolean strict) {

}
