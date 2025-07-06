package ai.agentscentral.openai.client.request.attributes;

/**
 * EnumFunctionProperty
 *
 * @param type
 * @param enums
 * @author Rizwan Idrees
 */
public record EnumFunctionProperty(String type, String[] enums) implements FunctionProperty {
}
