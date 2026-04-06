package ai.agentscentral.core.handoff;

import jakarta.annotation.Nonnull;

public record HandoffModule(@Nonnull HandoffsExtractor handoffsExtractor) {

    public static HandoffModule defaultHandoffModule() {
        return new HandoffModule(new HandoffsExtractor());
    }
}
