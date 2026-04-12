package ai.agentscentral.anthropic.client.response.attributes;

/**
 * The service tier that actually processed a request, as reported in the
 * {@link Usage#serviceTier()} field of the response.
 *
 * @author Rizwan Idrees
 */
public enum UsageServiceTier {
    /** Processed on the standard tier. */
    standard,
    /** Processed on the priority (higher-throughput) tier. */
    priority,
    /** Processed as part of a batch job. */
    batch
}
