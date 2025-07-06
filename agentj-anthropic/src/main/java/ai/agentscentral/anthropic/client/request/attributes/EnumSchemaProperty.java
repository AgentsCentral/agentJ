package ai.agentscentral.anthropic.client.request.attributes;

/**
 * EnumFunctionProperty
 *
 * @param type
 * @param enums
 * @author Rizwan Idrees
 */
public record EnumSchemaProperty(String type, String[] enums) implements SchemaProperty {
}
