package ai.agentscentral.anthropic.client;

import ai.agentscentral.anthropic.client.request.MessagesRequest;
import ai.agentscentral.anthropic.client.request.attributes.*;
import ai.agentscentral.anthropic.client.response.MessagesResponse;
import ai.agentscentral.anthropic.config.AnthropicConfig;
import org.junit.jupiter.api.Test;

import java.util.List;

import static ai.agentscentral.anthropic.client.request.attributes.AnthropicUserMessage.USER;
import static org.junit.jupiter.api.Assertions.*;

class AnthropicClientTest {

    private static final String API_KEY = System.getenv("ANTHROPIC_API_KEY");
    private static final String ANTHROPIC_VERSION = "2023-06-01";
    private static final String MODEL = "claude-sonnet-4-20250514";
    private static final Integer MAX_TOKENS = 1024;
    private final AnthropicConfig config = new AnthropicConfig(API_KEY, ANTHROPIC_VERSION, MAX_TOKENS);
    private AnthropicClient client = new AnthropicClient(config);


    @Test
    void should_respond_to_user_message_without_tools() {
        //Given
        final MessageContent content = new TextContent("What is the capital of United States");
        final List<AnthropicMessage> messages = List.of(new AnthropicUserMessage(USER , content));
        final SystemPrompt prompt = new TextSystemPrompt("You are an assistant. You are supposed to answer questions from users");
        final MessagesRequest request =  MessagesRequest.from(MODEL, config, prompt, "123", null, messages);


        //When
        final MessagesResponse response = client.sendMessages(request);


        //Then
        assertNotNull(response);
    }

}