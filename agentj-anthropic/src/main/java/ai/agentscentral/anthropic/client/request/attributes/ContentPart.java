package ai.agentscentral.anthropic.client.request.attributes;

/**
 * A single typed block within a {@link MessageContentParts} payload.
 *
 * <p>Concrete implementations:
 * <ul>
 *   <li>{@link TextContentPart} — a text block with type {@code "text"}</li>
 *   <li>{@link ToolResultContentPart} — the result of a previous tool call, with type
 *       {@code "tool_result"}</li>
 * </ul>
 *
 * @author Rizwan Idrees
 */
public interface ContentPart {

    /**
     * Returns the Anthropic content-part type string (e.g. {@code "text"},
     * {@code "tool_result"}).
     *
     * @return the type discriminator
     */
    String type();

}
