package ai.agentscentral.core.agent;


import ai.agentscentral.core.agent.instructor.Instructor;
import ai.agentscentral.core.agentic.Agentic;
import ai.agentscentral.core.handoff.Handoff;
import ai.agentscentral.core.model.Model;
import ai.agentscentral.core.tool.ToolBag;

import java.util.List;

/**
 * Agent
 *
 * @param name
 * @param model
 * @param instructors
 * @param toolBags
 * @param handoffs
 *
 * @author Rizwan Idrees
 */
public record Agent(String name,
                    Model model,
                    List<Instructor> instructors,
                    List<ToolBag> toolBags,
                    List<Handoff> handoffs) implements Agentic {
}
