package ai.agentscentral.anthropic.client.response.attributes;

/**
 * Identifies the built-in Anthropic server-side tool referenced in a
 * {@link ServerToolUseResponseContent} block.
 *
 * @author Rizwan Idrees
 */
public enum ServerToolUseName {
    /** The Anthropic-hosted web-search tool. */
    web_search,
    /** The Anthropic-hosted code-execution sandbox. */
    code_execution
}
