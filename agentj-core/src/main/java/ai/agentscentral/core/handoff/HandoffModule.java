package ai.agentscentral.core.handoff;

import jakarta.annotation.Nonnull;

/**
 * Groups the handoff infrastructure components required by the agentic execution framework.
 *
 * <p>Held inside {@link ai.agentscentral.core.agentic.AgenticModule} and consumed by
 * {@link ai.agentscentral.core.agentic.executor.DefaultAgentExecutor} at initialisation to
 * index an agent's declared {@link Handoff} targets. A default instance backed by the
 * standard {@link HandoffsExtractor} is available via {@link #defaultHandoffModule()}.</p>
 *
 * @param handoffsExtractor the extractor used to index handoff declarations by id
 *
 * @author Rizwan Idrees
 */
public record HandoffModule(@Nonnull HandoffsExtractor handoffsExtractor) {

    /**
     * Creates a {@code HandoffModule} using the default {@link HandoffsExtractor}.
     *
     * @return a new module with the standard handoff extractor
     */
    public static HandoffModule defaultHandoffModule() {
        return new HandoffModule(new HandoffsExtractor());
    }
}
