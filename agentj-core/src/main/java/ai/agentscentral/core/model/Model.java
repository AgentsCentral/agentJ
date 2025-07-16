package ai.agentscentral.core.model;

import jakarta.annotation.Nonnull;

/**
 * Model
 *
 * @param name
 * @param config
 * @author Rizwan Idrees
 */
public record Model(@Nonnull String name, @Nonnull ModelConfig config) {

}
