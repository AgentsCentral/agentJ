package ai.agentscentral.core.team;

import ai.agentscentral.core.agent.Agent;
import ai.agentscentral.core.agentic.Agentic;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * Team
 *
 * @param name
 * @param leader
 * @param members
 * @author Rizwan Idrees
 */
public record Team(@Nonnull String name,
                   @Nonnull Agent leader,
                   @Nonnull List<Agentic> members,
                   @Nonnull TeamMode mode) implements Agentic {
}


