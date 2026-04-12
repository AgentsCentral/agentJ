package ai.agentscentral.core.tool;

import ai.agentscentral.core.session.user.User;

/**
 * Functional interface for a pre-interrupt call — a function invoked before an interrupt
 * prompt is presented to the user, fetching contextual data to enrich the prompt.
 *
 * <p>Implementations are registered by name in
 * {@link ai.agentscentral.core.agentic.executor.register.InterruptPreCallRegistrar}
 * and referenced from {@link ai.agentscentral.core.annotation.Interrupt#interruptPreCalls()}.
 * At runtime, {@link ai.agentscentral.core.agentic.executor.DefaultAgentExecutor} invokes
 * each registered function and attaches the result as an
 * {@link ai.agentscentral.core.session.message.InterruptPreCallResult} on the
 * {@link ai.agentscentral.core.session.message.ToolInterruptPart}.</p>
 *
 * @param <T> the type of the contextual data returned by this pre-call function
 *
 * @author Rizwan Idrees
 */
@FunctionalInterface
public interface PreInterruptCall<T> {

    /**
     * Fetches contextual data for the interrupt prompt.
     *
     * @param user the current end-user; may be used to personalize or restrict the data
     * @return the contextual data to attach to the interrupt prompt; may be {@code null}
     */
    T call(User user);

}
