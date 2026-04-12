package ai.agentscentral.core.provider;

/**
 * Marker interface for LLM provider HTTP clients.
 *
 * <p>Each provider module (e.g. {@code agentj-anthropic}, {@code agentj-openai}) supplies
 * a concrete implementation — for example {@code AnthropicClient} or {@code OpenAIClient}
 * — that wraps the provider's REST API. The marker allows provider components to be
 * typed and discovered consistently across modules without coupling the core framework
 * to any specific client implementation.</p>
 *
 * @author Rizwan Idrees
 */
public interface ProviderClient {
}
