package ai.agentscentral.openai.client.request.attributes;

/**
 * Marker interface for a single parameter schema entry within a
 * {@link FunctionParameters} properties map.
 *
 * <p>Concrete types:
 * <ul>
 *   <li>{@link TypedFunctionProperty} — a parameter with a JSON type and description</li>
 *   <li>{@link EnumFunctionProperty} — a parameter constrained to a set of string
 *       values</li>
 * </ul>
 *
 * @author Rizwan Idrees
 */
public interface FunctionProperty {
}
