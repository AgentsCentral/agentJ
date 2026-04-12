package ai.agentscentral.anthropic.client.response.attributes;

/**
 * Discriminator enum for the {@code type} field of a
 * {@link ResponseContent} block in an Anthropic Messages response.
 *
 * @author Rizwan Idrees
 */
public enum ResponseContentType {
    /** A plain-text output block. */
    text,
    /** An extended-thinking reasoning block. */
    thinking,
    /** A redacted extended-thinking block (content is not accessible). */
    redacted_thinking,
    /** A tool or handoff invocation block. */
    tool_use,
    /** A server-side tool invocation block (e.g. code execution). */
    server_tool_use,
    /** The result of a server-side web-search tool invocation. */
    web_search_tool_result
}
