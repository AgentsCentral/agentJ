package ai.agentscentral.core.team;

import ai.agentscentral.core.agent.Agent;
import ai.agentscentral.core.agentic.Agentic;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

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
                   @Nullable Agent leader,
                   @Nonnull List<Agentic> members,
                   @Nonnull TeamMode mode) implements Agentic {
}


