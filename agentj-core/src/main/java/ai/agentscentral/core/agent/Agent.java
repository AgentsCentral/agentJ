package ai.agentscentral.core.agent;


import ai.agentscentral.core.agent.instructor.Instructor;
import ai.agentscentral.core.agentic.Agentic;
import ai.agentscentral.core.handoff.Handoff;
import ai.agentscentral.core.model.Model;
import ai.agentscentral.core.tool.ToolBag;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * Represents an agent — the primary execution implementation of implement {@link ai.agentscentral.core.agentic.Agentic} in the AgentJ framework.
 *
 * <p>An {@code Agent} combines a named identity, an LLM {@link Model}, one or more
 * {@link Instructor} functions that form its system prompt, an optional set of
 * {@link ToolBag}s exposing callable tools, and optional {@link Handoff} targets
 * that allow it to delegate a conversation to another agent or team.</p>
 *
 * <p>Agents implement {@link ai.agentscentral.core.agentic.Agentic} and can therefore
 * act as standalone executors or as members/leaders within a
 * {@link ai.agentscentral.core.team.Team}.</p>
 *
 * @param name        unique name identifying this agent within its scope; used for
 *                    routing, registration, and handoff resolution
 * @param model       the LLM model (name + provider configuration) the agent uses
 *                    to process messages and decide on tool calls or handoffs
 * @param instructors ordered list of {@link Instructor} functions whose outputs are
 *                    concatenated to form the agent's system prompt
 * @param toolBags    optional collections of {@link ToolBag} instances whose
 *                    {@code @Tool}-annotated methods are available for the agent to
 *                    invoke; may be {@code null} or empty for a tool-less agent
 * @param handoffs    optional handoff definitions declaring other agents or teams
 *                    this agent may delegate to; may be {@code null} or empty
 *
 * @author Rizwan Idrees
 */
public record Agent(@Nonnull String name,
                    @Nonnull Model model,
                    @Nonnull List<Instructor> instructors,
                    List<ToolBag> toolBags,
                    List<Handoff> handoffs) implements Agentic {
}
