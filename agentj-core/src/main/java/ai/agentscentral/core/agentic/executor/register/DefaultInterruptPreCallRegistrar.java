package ai.agentscentral.core.agentic.executor.register;

import ai.agentscentral.core.tool.PreInterruptCall;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * DefaultInterruptPreCallRegistrar
 *
 * @author Rizwan Idrees
 */
public class DefaultInterruptPreCallRegistrar implements InterruptPreCallRegistrar {

    private final Map<String, PreInterruptCall> preCallsRegister = new HashMap<>();

    @Override
    public void register(String name, PreInterruptCall preInterruptCall) {
        preCallsRegister.putIfAbsent(name, preInterruptCall);
    }


    @Override
    public Optional<PreInterruptCall> findByName(String name) {
        return Optional.ofNullable(preCallsRegister.get(name));
    }
}
