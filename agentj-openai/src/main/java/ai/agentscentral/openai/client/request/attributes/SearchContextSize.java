package ai.agentscentral.openai.client.request.attributes;

/**
 * Controls the amount of context retrieved during a web search, balancing breadth
 * against latency and cost.
 *
 * @author Rizwan Idrees
 */
public enum SearchContextSize {

    /** Minimal context — fastest search, fewest retrieved results. */
    low,

    /** Balanced context size between breadth and speed. */
    medium,

    /** Maximum context — broadest search, most retrieved results. */
    high
}
