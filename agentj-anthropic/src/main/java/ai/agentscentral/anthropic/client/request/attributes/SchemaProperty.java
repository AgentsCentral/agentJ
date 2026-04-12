package ai.agentscentral.anthropic.client.request.attributes;

/**
 * Marker interface for a single property definition within an {@link InputSchema}.
 *
 * <p>Concrete implementations:
 * <ul>
 *   <li>{@link TypedSchemaProperty} — for primitive and object parameters with a JSON
 *       type and optional description</li>
 *   <li>{@link EnumSchemaProperty} — for enum parameters with an explicit list of
 *       allowed values</li>
 * </ul>
 *
 * @author Rizwan Idrees
 */
public interface SchemaProperty {
}
