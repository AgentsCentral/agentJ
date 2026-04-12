package ai.agentscentral.anthropic.client.request.attributes;

/**
 * {@link SchemaProperty} for a primitive or object parameter, carrying a JSON type
 * string and an optional description.
 *
 * @param type        the JSON Schema type (e.g. {@code "string"}, {@code "integer"},
 *                    {@code "object"})
 * @param description a human-readable description of the parameter; may be {@code null}
 *
 * @author Rizwan Idrees
 */
public record TypedSchemaProperty(String type, String description) implements SchemaProperty {

}
