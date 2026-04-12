package ai.agentscentral.openai.client.request.attributes;

/**
 * Marker interface for a single typed part within a {@link MessageContents} list.
 *
 * <p>Concrete types:
 * <ul>
 *   <li>{@link TextContentPart} — a plain text segment</li>
 *   <li>{@link RefusalContentPart} — a refusal segment</li>
 * </ul>
 *
 * @author Rizwan Idrees
 */
public interface ContentPart {
}
