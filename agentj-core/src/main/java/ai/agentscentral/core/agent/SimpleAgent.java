package ai.agentscentral.core.agent;


import ai.agentscentral.core.agent.instructions.Instructor;
import ai.agentscentral.core.handoff.Handoff;
import ai.agentscentral.core.model.Model;
import ai.agentscentral.core.tool.ToolBag;

import java.util.List;

/**
 * SimpleAgent
 *
 * @param name
 * @param model
 * @param instructor
 * @param toolBags
 * @param handoffs
 *
 * @author Rizwan Idrees
 */
public record SimpleAgent(String name,
                          Model model,
                          List<Instructor> instructors,
                          List<ToolBag> toolBags,
                          List<Handoff> handoffs) implements Agent {
}
