package ai.agentscentral.core.handoff;

/**
 * Declares a handoff target that an {@link ai.agentscentral.core.agent.Agent} may
 * delegate a conversation to.
 *
 * <p>Handoffs are declared on an agent at construction time. At initialisation,
 * {@link HandoffsExtractor} indexes them by {@code id} and passes the resulting map to
 * the model provider so the LLM can emit a handoff instruction when it decides the current
 * agent should transfer control. The framework then uses
 * {@link ai.agentscentral.core.agentic.executor.DefaultHandoffExecutor} to resolve the
 * target by {@code agenticName} and switch the active executor accordingly.</p>
 *
 * @param id           unique identifier for this handoff, used as the tool/function call id
 *                     by the model provider when emitting a handoff instruction
 * @param agenticName  name of the target {@link ai.agentscentral.core.agentic.Agentic}
 *                     entity (agent or team) to transfer control to; must match a name
 *                     registered in the {@link ai.agentscentral.core.agentic.executor.register.AgenticRegistrar}
 * @param description  human-readable description of when and why to use this handoff,
 *                     sent to the model to guide its routing decisions
 *
 * @author Rizwan Idrees
 */
public record Handoff(String id, String agenticName, String description) {
}
