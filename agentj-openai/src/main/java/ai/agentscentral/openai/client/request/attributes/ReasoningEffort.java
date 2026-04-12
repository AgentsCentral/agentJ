package ai.agentscentral.openai.client.request.attributes;

/**
 * Controls the amount of effort the model spends on reasoning for models that support
 * chain-of-thought reasoning.
 *
 * @author Rizwan Idrees
 */
public enum ReasoningEffort {

    /** Minimal reasoning — fastest, lowest token cost. */
    low,

    /** Balanced reasoning between speed and quality. */
    medium,

    /** Maximum reasoning — most thorough, highest token cost. */
    high
}
