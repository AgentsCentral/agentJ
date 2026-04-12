package ai.agentscentral.core.agentic.executor.register;

import ai.agentscentral.core.tool.PreInterruptCall;

import java.util.Optional;

/**
 * Registry for {@link PreInterruptCall} functions invoked before a tool-call interrupt
 * is presented to the user.
 *
 * <p>Pre-interrupt calls are named functions that fetch contextual data (e.g. current
 * state, user preferences) and attach the results to the interrupt prompt so the user has
 * enough information to respond. They are referenced by name in the {@code @Interrupt}
 * annotation's {@code interruptPreCalls} attribute and resolved here at runtime.</p>
 *
 * @author Rizwan Idrees
 */
public interface InterruptPreCallRegistrar {

    /**
     * Registers a named pre-interrupt call function.
     *
     * @param <T>               return type of the pre-interrupt call
     * @param name              unique name used to look up this function at runtime
     * @param preInterruptCall  the function to register
     */
    <T> void register(String name, PreInterruptCall<T> preInterruptCall);

    /**
     * Looks up a pre-interrupt call function by name.
     *
     * @param <T>  expected return type of the function
     * @param name the name under which the function was registered
     * @return an {@code Optional} containing the function, or empty if not found
     */
    <T> Optional<PreInterruptCall<T>> findByName(String name);

}
