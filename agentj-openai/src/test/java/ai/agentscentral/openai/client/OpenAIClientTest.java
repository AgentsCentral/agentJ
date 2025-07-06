package ai.agentscentral.openai.client;

import ai.agentscentral.openai.client.request.CompletionRequest;
import ai.agentscentral.openai.client.request.attributes.*;
import ai.agentscentral.openai.client.response.CompletionResponse;
import ai.agentscentral.openai.client.response.parameters.Function;
import ai.agentscentral.openai.client.response.parameters.ToolCall;
import ai.agentscentral.openai.config.OpenAIConfig;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static ai.agentscentral.openai.client.request.attributes.OpenAITool.FUNCTION;
import static ai.agentscentral.openai.client.request.attributes.OpenAIUserMessage.USER;
import static org.junit.jupiter.api.Assertions.*;

class OpenAIClientTest {

    private static final String OPEN_AI_KEY = System.getenv("OPEN_AI_KEY");

    private static final String GPT_4_O_MINI = "gpt-4o-mini";
    private final OpenAIConfig config = new OpenAIConfig(OPEN_AI_KEY);
    private OpenAIClient client = new OpenAIClient(config.getUrl(), config.getApiKey());

    @Test
    void should_complete_user_message_without_tools() {
        //Given
        final MessageContent content = new TextContent("What is the capital of United States");
        final List<OpenAIMessage> messages = List.of(new OpenAIUserMessage( USER, null, content));
        final CompletionRequest request = CompletionRequest.from(GPT_4_O_MINI, config, null, List.of(), messages);

        //When
        final CompletionResponse response = client.complete(request);


        //Then
        assertNotNull(response);
    }

    @Test
    void should_complete_user_message_with_tools() {
        //Given
        final MessageContent content = new TextContent("How is the weather at New York");
        final Map<String, FunctionProperty> properties = Map.of("city",
                new TypedFunctionProperty("string", "name of the city"));
        final FunctionParameters parameters = new FunctionParameters("object", properties, Set.of("city"));
        final ToolFunction function = new ToolFunction("weather_tool",
                "Checks the weather of a city", parameters, false);
        final OpenAITool tool = new OpenAITool(FUNCTION, function);


        final List<OpenAIMessage> messages = List.of(new OpenAIUserMessage(USER, null, content));
        final CompletionRequest request = CompletionRequest.from(GPT_4_O_MINI, config, null, List.of(tool), messages);

        //When
        final CompletionResponse response = client.complete(request);


        //Then
        assertNotNull(response);
        assertNotNull(response.choices());
        assertFalse(response.choices().isEmpty());
        assertNotNull(response.choices().getFirst().message());

        final List<ToolCall> toolCalls = response.choices().getFirst().message().toolCalls();
        assertNotNull(toolCalls);
        assertFalse(toolCalls.isEmpty());

        final ToolCall firstTool = toolCalls.getFirst();
        final Function toolCallFunction = firstTool.function();
        assertEquals(function.name(), toolCallFunction.name());

        assertNotNull(toolCallFunction.arguments());
        assertEquals("{\"city\":\"New York\"}", toolCallFunction.arguments());
    }

}