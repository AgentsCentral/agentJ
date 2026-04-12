package ai.agentscentral.core.annotation;

import java.lang.annotation.Repeatable;

import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * Declares a single interrupt that pauses tool execution and prompts the user
 * for confirmation or additional input before the tool method is invoked.
 *
 * <p>One or more {@code @Interrupt} declarations are placed on a {@link Tool}-annotated
 * method via the {@link Tool#interruptsBefore()} attribute (or directly as a repeatable
 * annotation). When the LLM decides to call that tool,
 * {@link ai.agentscentral.core.agentic.executor.DefaultAgentExecutor} detects the
 * interrupt, builds a {@link ai.agentscentral.core.session.message.ToolInterruptMessage},
 * and waits for the user to respond with a
 * {@link ai.agentscentral.core.session.message.UserMessage} containing
 * {@link ai.agentscentral.core.session.message.UserInterruptPart} values before
 * proceeding with execution.</p>
 *
 * <p>This annotation is {@link Repeatable}; multiple interrupts on the same tool are
 * grouped in the container annotation {@link Interrupts}.</p>
 *
 * @author Rizwan Idrees
 */
@Repeatable(Interrupts.class)
public @interface Interrupt {

    /**
     * Unique name identifying this interrupt within the tool.
     *
     * <p>Used to match the interrupt with the user's response at runtime.</p>
     *
     * @return the interrupt name
     */
    String name();

    /**
     * Reference to the UI renderer used to display this interrupt prompt to the user.
     *
     * <p>Defaults to an empty string, which indicates the default renderer should be used.</p>
     *
     * @return the renderer reference, or an empty string for the default
     */
    String rendererReference() default EMPTY;

    /**
     * Names of the tool's input arguments to pass as context when rendering the interrupt.
     *
     * <p>These are drawn from the tool call's resolved arguments and made available
     * to the renderer so the user sees relevant context before responding.</p>
     *
     * @return the argument names to include; defaults to an empty array
     */
    String[] params() default {};

    /**
     * Names of {@link ai.agentscentral.core.tool.PreInterruptCall} functions, registered
     * in {@link ai.agentscentral.core.agentic.executor.register.InterruptPreCallRegistrar},
     * to execute before the interrupt is presented to the user.
     *
     * <p>Pre-call results (e.g. current state, user preferences) are attached to the
     * interrupt prompt as {@link ai.agentscentral.core.session.message.InterruptPreCallResult}
     * entries, giving the user additional context to make their decision.</p>
     *
     * @return the pre-call function names; defaults to an empty array
     */
    String[] interruptPreCalls() default {};

}
