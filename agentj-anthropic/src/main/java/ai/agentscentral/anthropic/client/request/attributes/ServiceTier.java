package ai.agentscentral.anthropic.client.request.attributes;

/**
 * Service-tier preference for an Anthropic Messages request.
 *
 * <p>Sent as the {@code service_tier} field when set on {@link AnthropicConfig}.</p>
 *
 * @author Rizwan Idrees
 */
public enum ServiceTier {
    /** Let Anthropic choose the optimal tier automatically. */
    auto,
    /** Restrict processing to the standard (non-priority) tier. */
    standard_only
}
