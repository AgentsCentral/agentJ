package ai.agentscentral.core.tool;

/**
 * Holds the outcome of a successful tool method invocation.
 *
 * <p>Created by {@link DefaultToolCallExecutor} after a successful reflective method call
 * and wrapped in a {@link ResultOrError}. The result and return-type metadata are then
 * converted into a {@link ai.agentscentral.core.session.message.ToolMessage} by
 * {@link ai.agentscentral.core.tool.convertors.ToolConvertors#convertToolCallResultToToolMessage}
 * for inclusion in the context sent to the provider on the next pass.</p>
 *
 * <p>The default implementation is {@link DefaultToolCallResult}.</p>
 *
 * @author Rizwan Idrees
 */
public interface ToolCallResult {

    /**
     * Returns the original {@link ToolCallInstruction} that triggered this invocation.
     *
     * <p>Used to extract the call id and tool name when constructing the
     * {@link ai.agentscentral.core.session.message.ToolMessage}.</p>
     *
     * @return the instruction; never {@code null}
     */
    ToolCallInstruction instruction();

    /**
     * Returns the value returned by the tool method.
     *
     * @return the method's return value; may be {@code null} for {@code void} methods
     */
    Object result();

    /**
     * Returns the declared return type of the tool method.
     *
     * <p>Available for serialisation or type-aware conversion by the provider layer.</p>
     *
     * @return the return type {@link Class}; never {@code null}
     */
    Class<?> returnType();

}
