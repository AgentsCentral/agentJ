package ai.agentscentral.core.model;

import jakarta.annotation.Nonnull;

/**
 * Identifies the LLM model to use and carries the provider-specific configuration
 * needed to connect to it.
 *
 * <p>A {@code Model} is declared on an {@link ai.agentscentral.core.agent.Agent} and is
 * consumed at initialization time by
 * {@link ai.agentscentral.core.agentic.executor.DefaultAgentExecutor}, which calls
 * {@link ModelConfig#createAgentExecutor} to obtain the
 * {@link ai.agentscentral.core.provider.ProviderAgentExecutor} that will drive the actual
 * LLM requests for that agent.</p>
 *
 * @param name   the model identifier sent to the provider (e.g. {@code "claude-sonnet-4-6"}
 *               or {@code "gpt-4o"}); must match a model name accepted by the configured
 *               provider
 * @param config provider-specific configuration (API credentials, endpoint, version, etc.)
 *               that knows how to create the corresponding
 *               {@link ai.agentscentral.core.provider.ProviderAgentExecutor}
 *
 * @author Rizwan Idrees
 */
public record Model(@Nonnull String name, @Nonnull ModelConfig config) {

}
