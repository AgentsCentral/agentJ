package ai.agentscentral.core.tool;

/**
 * Describes a single user-supplied parameter of a {@link ToolCall}, extracted from an
 * {@link ai.agentscentral.core.annotation.InterruptParam}-annotated method parameter.
 *
 * <p>Unlike {@link ToolParameter} (which the LLM fills in), interrupt parameters are
 * collected from the user's response to a
 * {@link ai.agentscentral.core.session.message.ToolInterruptPart} and injected into the
 * tool method by {@link DefaultToolCallExecutor}.</p>
 *
 * <p>Concrete implementations are selected by {@link ReflectionUtil} based on the Java
 * type: {@link TypedInterruptParameter}, {@link CollectionInterruptParameter},
 * {@link ArrayInterruptParameter}, and {@link EnumInterruptParameter}.</p>
 *
 * @author Rizwan Idrees
 */
public interface InterruptParameter {

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
     * Returns the parameter name as declared in
     * {@link ai.agentscentral.core.annotation.InterruptParam#name()}, used to match
     * the user's interrupt response value.
     *
     * @return the parameter name; never {@code null}
     */
    String name();

    /**
     * Returns whether the user must supply a value for this parameter.
     *
     * @return {@code true} if the parameter is required
     */
    boolean required();

}
