package ai.agentscentral.openai.client.request.attributes;

/**
 * TypedFunctionProperty
 *
 * @param type
 * @param description
 * @author Rizwan Idrees
 */
public record TypedFunctionProperty(String type, String description) implements FunctionProperty {

}
