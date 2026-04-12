package ai.agentscentral.anthropic.client.request.attributes;

/**
 * {@link SchemaProperty} for an enum parameter, carrying the JSON type string and the
 * array of permitted values.
 *
 * @param type  the JSON Schema type (typically {@code "string"})
 * @param enums the allowed enum constant names as strings
 *
 * @author Rizwan Idrees
 */
public record EnumSchemaProperty(String type, String[] enums) implements SchemaProperty {
}
