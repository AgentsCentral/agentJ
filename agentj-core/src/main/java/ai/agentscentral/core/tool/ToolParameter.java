package ai.agentscentral.core.tool;

/**
 * Describes a single LLM-supplied parameter of a {@link ToolCall}, extracted from a
 * {@link ai.agentscentral.core.annotation.ToolParam}-annotated method parameter.
 *
 * <p>Concrete implementations are selected by {@link ReflectionUtil} based on the
 * parameter's Java type:
 * {@link TypedToolParameter} (primitives and general objects),
 * {@link CollectionToolParameter} ({@link java.util.Collection} subtypes),
 * {@link ArrayToolParameter} (arrays), and
 * {@link EnumToolParameter} (enums, which additionally expose their constant values).</p>
 *
 * <p>The schema metadata ({@link #name()}, {@link #description()}, {@link #required()},
 * {@link #type()}) is sent to the model provider so the LLM knows what arguments to
 * supply. The {@link #index()} is used to order parameters correctly when building the
 * method invocation array.</p>
 *
 * @author Rizwan Idrees
 */
public interface ToolParameter {

    /**
     * Returns the zero-based position of this parameter in the tool method's parameter list.
     *
     * @return the parameter index
     */
    int index();

    /**
     * Returns the Java type of this parameter.
     *
     * @return the parameter's {@link Class}; never {@code null}
     */
    Class<?> type();

    /**
     * Returns the parameter name as declared in {@link ai.agentscentral.core.annotation.ToolParam#name()},
     * sent to the model provider as the argument key.
     *
     * @return the parameter name; never {@code null}
     */
    String name();

    /**
     * Returns the parameter description sent to the model provider.
     *
     * @return the parameter description; never {@code null}
     */
    String description();

    /**
     * Returns whether the model must supply a value for this parameter.
     *
     * @return {@code true} if the parameter is required
     */
    boolean required();

}
