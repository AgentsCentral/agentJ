package ai.agentscentral.openai.client.request.attributes;

/**
 * {@link FunctionProperty} for a parameter constrained to a fixed set of string values
 * (an enum parameter).
 *
 * @param type  JSON schema type string (typically {@code "string"})
 * @param enums the allowed string values for this parameter
 *
 * @author Rizwan Idrees
 */
public record EnumFunctionProperty(String type, String[] enums) implements FunctionProperty {
}
