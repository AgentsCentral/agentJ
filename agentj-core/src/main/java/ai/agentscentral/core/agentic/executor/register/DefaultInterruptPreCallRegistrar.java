package ai.agentscentral.core.agentic.executor.register;

import ai.agentscentral.core.tool.PreInterruptCall;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Default implementation of {@link InterruptPreCallRegistrar} backed by a {@link java.util.HashMap}.
 *
 * <p>Registrations are idempotent: a second {@link #register} call for the same name is
 * silently ignored, preserving the first-registered function.</p>
 *
 * @author Rizwan Idrees
 */
public class DefaultInterruptPreCallRegistrar implements InterruptPreCallRegistrar {

    private final Map<String, PreInterruptCall<?>> preCallsRegister = new HashMap<>();

    @Override
    public <T> void register(String name, PreInterruptCall<T> preInterruptCall) {
        preCallsRegister.putIfAbsent(name, preInterruptCall);
    }


    @Override
    @SuppressWarnings("unchecked")
    public <T> Optional<PreInterruptCall<T>> findByName(String name) {
        return Optional.ofNullable( (PreInterruptCall<T>) preCallsRegister.get(name));
    }
}
