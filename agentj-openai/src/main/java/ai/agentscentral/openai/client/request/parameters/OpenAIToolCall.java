package ai.agentscentral.openai.client.request.parameters;

/**
 * OpenAIToolCall
 * @param id
 * @param type
 * @param function
 * @author Rizwan Idrees
 */
public record OpenAIToolCall(String id, String type, ToolCallFunction function) {
}
