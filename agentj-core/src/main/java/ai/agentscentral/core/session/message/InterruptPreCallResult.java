package ai.agentscentral.core.session.message;

/**
 * Holds the result of a pre-interrupt call executed before a tool interrupt is
 * presented to the user.
 *
 * <p>When a {@link ai.agentscentral.core.annotation.Interrupt} declares
 * {@link ai.agentscentral.core.annotation.Interrupt#interruptPreCalls()}, the framework
 * invokes the corresponding
 * {@link ai.agentscentral.core.tool.PreInterruptCall} functions before rendering the
 * interrupt prompt. Each result is wrapped in an {@code InterruptPreCallResult} and
 * attached to the {@link ToolInterruptPart} so the UI renderer can display contextual
 * information alongside the interrupt prompt.</p>
 *
 * @param name   name of the pre-interrupt call, matching the registered name in
 *               {@link ai.agentscentral.core.agentic.executor.register.InterruptPreCallRegistrar}
 * @param result the value returned by the pre-interrupt call; may be {@code null} if
 *               the call returned no result
 *
 * @author Rizwan Idrees
 */
public record InterruptPreCallResult(String name, Object result) {
}
