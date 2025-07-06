package ai.agentscentral.anthropic.client;

import ai.agentscentral.anthropic.client.request.MessagesRequest;
import ai.agentscentral.anthropic.client.request.attributes.*;
import ai.agentscentral.anthropic.client.response.MessagesResponse;
import ai.agentscentral.anthropic.client.response.attributes.ResponseContent;
import ai.agentscentral.anthropic.client.response.attributes.TextResponseContent;
import ai.agentscentral.anthropic.client.response.attributes.ToolUseResponseContent;
import ai.agentscentral.anthropic.config.AnthropicConfig;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static ai.agentscentral.anthropic.client.request.attributes.AnthropicUserMessage.USER;
import static ai.agentscentral.anthropic.client.response.attributes.ResponseContentType.text;
import static ai.agentscentral.anthropic.client.response.attributes.ResponseContentType.tool_use;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;
import static org.junit.jupiter.api.Assertions.*;

class AnthropicClientTest {

    private static final String API_KEY = System.getenv("ANTHROPIC_API_KEY");
    private static final String ANTHROPIC_VERSION = "2023-06-01";
    private static final String MODEL = "claude-sonnet-4-20250514";
    private static final Integer MAX_TOKENS = 1024;
    private final AnthropicConfig config = new AnthropicConfig(API_KEY, ANTHROPIC_VERSION, MAX_TOKENS);
    private final AnthropicClient client = new AnthropicClient(config);


    @Test
    void should_respond_to_user_message_without_tools() {
        //Given
        final MessageContent content = new TextContent("What is the capital of United States");
        final List<AnthropicMessage> messages = List.of(new AnthropicUserMessage(USER, content));

        final SystemPrompt prompt = new TextSystemPrompt("You are an assistant. You are supposed to answer questions from users");
        final MessagesRequest request = MessagesRequest.from(MODEL, config, prompt, "123", null, messages);


        //When
        final MessagesResponse response = client.sendMessages(request);


        //Then
        assertNotNull(response);
        assertNotNull(response.id());
        assertNotNull(response.content());

        final ResponseContent firstContentPart = response.content().getFirst();
        assertNotNull(firstContentPart);
        assertInstanceOf(TextResponseContent.class, firstContentPart);

        final TextResponseContent textResponseContent = (TextResponseContent) firstContentPart;
        assertEquals(text, textResponseContent.type());
        assertTrue(containsIgnoreCase(textResponseContent.text(), "Washington"));
    }


    @Test
    void should_complete_user_message_with_tools() {
        //Given
        final Map<String, SchemaProperty> properties = Map.of("city",
                new TypedSchemaProperty("string", "name of the city"));

        final InputSchema inputSchema = new InputSchema("object", properties, Set.of("city"));
        final AnthropicTool tool = new AnthropicTool("weather_tool",
                "Get the current weather in a given city", "custom", inputSchema);


        final MessageContent content = new TextContent("How is the weather at New York");
        final List<AnthropicMessage> messages = List.of(new AnthropicUserMessage(USER, content));

        final SystemPrompt prompt = new TextSystemPrompt("You are a weather assistant. You are supposed to answer questions from users about the weather in different cities");
        final MessagesRequest request = MessagesRequest.from(MODEL, config, prompt, "123", List.of(tool), messages);

        //When
        final MessagesResponse response = client.sendMessages(request);


        //Then
        assertNotNull(response);
        assertNotNull(response.content());
        assertFalse(response.content().isEmpty());

        final Optional<ToolUseResponseContent> toolUseResponseContent = response.content().stream()
                .filter(c -> c instanceof ToolUseResponseContent)
                .findFirst()
                .map(c -> (ToolUseResponseContent) c);

        assertTrue(toolUseResponseContent.isPresent());

        final ToolUseResponseContent toolUse = toolUseResponseContent.get();
        assertNotNull(toolUse.id());
        assertEquals(tool.name(), toolUse.name());
        assertEquals(tool_use, toolUse.type());
        assertNotNull(toolUse.input());
        assertEquals(Map.of("city", "New York"), toolUse.input());
    }

}