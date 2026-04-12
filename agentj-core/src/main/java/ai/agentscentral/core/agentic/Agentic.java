package ai.agentscentral.core.agentic;

/**
 * Base interface for any entity that can participate in agentic execution.
 *
 * <p>Both {@link ai.agentscentral.core.agent.Agent} (a single LLM-backed agent) and
 * {@link ai.agentscentral.core.team.Team} (a coordinated group of agents) implement this
 * interface, allowing executors, registrars, and routing logic to treat them uniformly.</p>
 *
 * @author Rizwan Idrees
 */
public interface Agentic {

    /**
     * Returns the unique name of this agentic entity.
     *
     * <p>The name is used for registration, handoff resolution, and routing within teams.</p>
     *
     * @return the entity's name; never {@code null}
     */
    String name();

}
