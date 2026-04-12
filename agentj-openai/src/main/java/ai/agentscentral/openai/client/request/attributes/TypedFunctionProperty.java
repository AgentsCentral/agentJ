package ai.agentscentral.openai.client.request.attributes;

/**
 * {@link FunctionProperty} for a parameter that has a JSON primitive type (e.g.
 * {@code "string"}, {@code "integer"}, {@code "boolean"}) and a textual description.
 *
 * @param type        JSON schema type string (e.g. {@code "string"}, {@code "number"})
 * @param description human-readable description of what the parameter represents
 *
 * @author Rizwan Idrees
 */
public record TypedFunctionProperty(String type, String description) implements FunctionProperty {

}
