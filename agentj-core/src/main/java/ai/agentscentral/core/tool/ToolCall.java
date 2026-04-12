package ai.agentscentral.core.tool;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * Immutable descriptor of a single callable tool, built by {@link ToolBagToolsExtractor}
 * from a {@link ai.agentscentral.core.annotation.Tool}-annotated method.
 *
 * <p>A {@code ToolCall} aggregates all the reflection metadata needed by
 * {@link DefaultToolCallExecutor} to invoke the tool method, alongside the schema
 * metadata (name, description, parameters) that is sent to the model provider so the LLM
 * can decide when and how to call the tool.</p>
 *
 * @param toolBag             the {@link ToolBag} instance that owns the tool method;
 *                            used as the receiver when invoking the method via reflection
 * @param method              the reflected {@link java.lang.reflect.Method} to invoke
 * @param name                the tool name as declared in {@link ai.agentscentral.core.annotation.Tool#name()};
 *                            used as the map key and sent to the provider
 * @param description         the tool description sent to the provider to guide model decisions
 * @param parameters          LLM-supplied parameters extracted from {@link ai.agentscentral.core.annotation.ToolParam}
 *                            annotations; may be empty
 * @param interruptParameters user-supplied parameters extracted from
 *                            {@link ai.agentscentral.core.annotation.InterruptParam} annotations;
 *                            injected after the user responds to an interrupt; may be empty
 * @param interruptsBefore    pre-call interrupts declared via
 *                            {@link ai.agentscentral.core.annotation.Tool#interruptsBefore()};
 *                            may be empty when no interrupts are declared
 *
 * @author Rizwan Idrees
 */
public record ToolCall(ToolBag toolBag,
                       Method method,
                       String name,
                       String description,
                       List<ToolParameter> parameters,
                       List<InterruptParameter> interruptParameters,
                       List<ToolInterrupt> interruptsBefore) {

    /**
     * Returns {@code true} if this tool declares at least one pre-call interrupt.
     *
     * @return {@code true} when {@code interruptsBefore} is non-null and non-empty
     */
    public boolean hasInterruptsBefore(){
        return Objects.nonNull(interruptsBefore) && !interruptsBefore.isEmpty();
    }
}
