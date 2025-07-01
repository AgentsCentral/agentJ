package ai.agentscentral.openai.client.response.parameters;

/**
 * ToolCall
 *
 * @param id
 * @param type
 * @param function
 * @author Rizwan Idrees
 */
public record ToolCall(String id, String type, Function function) {
}
