package ai.agentscentral.openai.client.request.attributes;

/**
 * Marker interface for the {@code response_format} field of a
 * {@link ai.agentscentral.openai.client.request.CompletionRequest}.
 *
 * <p>Concrete types:
 * <ul>
 *   <li>{@link TextResponseFormat} — plain text output ({@code type: "text"})</li>
 *   <li>{@link JSONSchemaResponseFormat} — structured JSON output constrained by a
 *       schema ({@code type: "json_schema"})</li>
 * </ul>
 *
 * @author Rizwan Idrees
 */
public interface ResponseFormat {
}
