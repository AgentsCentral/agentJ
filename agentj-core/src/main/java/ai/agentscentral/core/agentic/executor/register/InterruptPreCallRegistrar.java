package ai.agentscentral.core.agentic.executor.register;

import ai.agentscentral.core.tool.PreInterruptCall;

import java.util.Optional;

/**
 * InterruptPreCallRegistrar
 *
 * @author Rizwan Idrees
 */
public interface InterruptPreCallRegistrar {

    <T> void register(String name, PreInterruptCall<T> preInterruptCall);

    <T> Optional<PreInterruptCall<T>> findByName(String name);

}
