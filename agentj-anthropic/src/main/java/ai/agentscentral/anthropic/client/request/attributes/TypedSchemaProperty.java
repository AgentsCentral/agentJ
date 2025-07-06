package ai.agentscentral.anthropic.client.request.attributes;

/**
 * TypedFunctionProperty
 *
 * @param type
 * @param description
 * @author Rizwan Idrees
 */
public record TypedSchemaProperty(String type, String description) implements SchemaProperty {

}
