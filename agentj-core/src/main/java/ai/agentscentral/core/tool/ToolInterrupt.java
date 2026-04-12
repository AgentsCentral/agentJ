package ai.agentscentral.core.tool;

import java.util.List;

/**
 * Runtime representation of an {@link ai.agentscentral.core.annotation.Interrupt}
 * declaration, extracted from a {@link ToolCall} at initialisation time.
 *
 * <p>Built by {@link ToolBagToolsExtractor} from the
 * {@link ai.agentscentral.core.annotation.Interrupt} annotations declared on a
 * {@link ai.agentscentral.core.annotation.Tool}-annotated method. At execution time,
 * {@link ai.agentscentral.core.agentic.executor.DefaultAgentExecutor} iterates the
 * {@link ToolCall#interruptsBefore()} list to construct
 * {@link ai.agentscentral.core.session.message.ToolInterruptPart}s for the interrupt
 * message presented to the user.</p>
 *
 * @param name              the interrupt name, matching
 *                          {@link ai.agentscentral.core.annotation.Interrupt#name()}
 * @param renderer          the UI renderer reference from
 *                          {@link ai.agentscentral.core.annotation.Interrupt#rendererReference()}
 * @param preInterruptCalls names of {@link PreInterruptCall} functions to invoke before
 *                          displaying this interrupt prompt
 * @param parameters        the {@link InterruptParameter}s whose values the user must
 *                          supply in their interrupt response
 *
 * @author Rizwan Idrees
 */
public record ToolInterrupt(String name,
                            String renderer,
                            List<String> preInterruptCalls,
                            List<InterruptParameter> parameters) {
}
