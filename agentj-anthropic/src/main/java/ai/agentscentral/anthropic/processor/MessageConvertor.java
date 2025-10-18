package ai.agentscentral.anthropic.processor;

import ai.agentscentral.anthropic.client.request.attributes.*;
import ai.agentscentral.anthropic.client.response.MessagesResponse;
import ai.agentscentral.anthropic.client.response.attributes.ResponseContent;
import ai.agentscentral.anthropic.client.response.attributes.TextResponseContent;
import ai.agentscentral.anthropic.client.response.attributes.ThinkingResponseContent;
import ai.agentscentral.anthropic.client.response.attributes.ToolUseResponseContent;
import ai.agentscentral.core.handoff.DefaultHandoffInstruction;
import ai.agentscentral.core.handoff.Handoff;
import ai.agentscentral.core.handoff.HandoffInstruction;
import ai.agentscentral.core.session.message.*;
import ai.agentscentral.core.tool.DefaultToolCallInstruction;
import ai.agentscentral.core.tool.ToolCall;
import ai.agentscentral.core.tool.ToolCallInstruction;
import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static ai.agentscentral.anthropic.client.common.Role.assistant;
import static ai.agentscentral.anthropic.client.request.attributes.AnthropicAssistantMessage.ASSISTANT;
import static ai.agentscentral.anthropic.client.request.attributes.AnthropicUserMessage.USER;
import static ai.agentscentral.anthropic.client.request.attributes.TextContentPart.TEXT;
import static ai.agentscentral.anthropic.client.request.attributes.ToolResultContentPart.TOOL_RESULT;
import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * MessageConvertor
 *
 * @author Rizwan Idrees
 */
class MessageConvertor {

    private final Map<String, ToolCall> tools;
    private final Map<String, Handoff> handOffs;

    public MessageConvertor(Map<String, ToolCall> tools,
                            Map<String, Handoff> handOffs) {
        this.tools = tools;
        this.handOffs = handOffs;
    }


    List<AnthropicMessage> toAnthropicMessages(List<Message> messages) {
        return messages.stream().map(this::toAnthropicMessage).toList();
    }


    AssistantMessage toAssistantMessage(String contextId, MessagesResponse response) {

        if (!assistant.equals(response.role())) {
            throw new UnsupportedOperationException("Message for role " + response.role() + "is not support");
        }

        final String messageId = response.id();
        final TextPart[] parts = Objects.isNull(response.content()) ? null : textParts(response.content());

        final List<ToolCallInstruction> toolCalls = response.hasToolCalls() ? toToolCallInstructions(response) : List.of();
        final List<HandoffInstruction> handoffCalls = response.hasToolCalls() ? toHandOffs(response) : List.of();
        return new AssistantMessage(contextId, messageId, parts, toolCalls, handoffCalls, System.currentTimeMillis());
    }

    private List<ToolCallInstruction> toToolCallInstructions(MessagesResponse response) {
        return response.content().stream()
                .filter(rc -> rc instanceof ToolUseResponseContent)
                .map(rc -> (ToolUseResponseContent) rc)
                .filter(toolUse -> tools.containsKey(toolUse.name()))
                .map(this::toToolCallInstruction).toList();
    }

    private List<HandoffInstruction> toHandOffs(MessagesResponse response) {
        return response.content().stream()
                .filter(rc -> rc instanceof ToolUseResponseContent)
                .map(rc -> (ToolUseResponseContent) rc)
                .filter(toolUse -> tools.containsKey(toolUse.name()))
                .map(this::toHandffInstruction)
                .toList();
    }

    private ToolCallInstruction toToolCallInstruction(ToolUseResponseContent toolUse) {
        return new DefaultToolCallInstruction(toolUse.id(),
                toolUse.name(),
                Objects.toString(toolUse.input(), "{}"),
                toolUse.input(),
                tools.get(toolUse.name()));
    }

    private HandoffInstruction toHandffInstruction(ToolUseResponseContent toolUse) {
        return new DefaultHandoffInstruction(toolUse.id(), handOffs.get(toolUse.name()));
    }

    private AnthropicMessage toAnthropicMessage(@Nonnull Message message) {

        return switch (message) {
            case UserMessage um -> new AnthropicUserMessage(USER, toTextMessageContent(um.parts()));
            case AssistantMessage am -> new AnthropicAssistantMessage(ASSISTANT, toTextMessageContent(am.parts()));
            case ToolMessage tm ->
                    new AnthropicUserMessage(USER, toToolResultMessageContent(tm.toolCallId(), tm.parts()));
            case HandOffMessage ho ->
                    new AnthropicUserMessage(USER, toToolResultMessageContent(ho.handOffId(), ho.parts()));
            default -> throw new RuntimeException("Cannot convert message of type " + message.getClass());
        };
    }

    private MessageContent toTextMessageContent(MessagePart[] parts) {
        if (Objects.isNull(parts)) {
            return new TextContent(EMPTY);
        }

        if (parts instanceof TextPart[] textParts) {
            final List<TextContentPart> contentParts = Stream.of(textParts)
                    .map(tp -> new TextContentPart(TEXT, tp.text())).toList();

            return new MessageContentParts(contentParts);
        }

        return new TextContent(EMPTY);
    }

    private MessageContent toToolResultMessageContent(String toolUseId, MessagePart[] parts) {
        if (parts instanceof TextPart[] textParts) {
            final List<ToolResultContentPart> contentParts = Stream.of(textParts)
                    .map(tp -> new ToolResultContentPart(TOOL_RESULT, toolUseId, tp.text())).toList();
            return new MessageContentParts(contentParts);
        }

        final ToolResultContentPart toolContentPart = new ToolResultContentPart(TOOL_RESULT,
                toolUseId, null);
        return new MessageContentParts(List.of(toolContentPart));
    }

    private TextPart[] textParts(List<ResponseContent> content) {
        return content.stream()
                .filter(rc -> rc instanceof TextResponseContent || rc instanceof ThinkingResponseContent)
                .map(this::toTextPart)
                .filter(Objects::nonNull)
                .toArray(TextPart[]::new);
    }

    private TextPart toTextPart(ResponseContent responseContent) {
        if (responseContent instanceof TextResponseContent tc) {
            return new TextPart(MessagePartType.text, tc.text());
        } else if (responseContent instanceof ThinkingResponseContent tr) {
            return new TextPart(MessagePartType.thinking, tr.thinking());
        }
        return null;
    }

}
