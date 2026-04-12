package ai.agentscentral.anthropic.executor;

import ai.agentscentral.anthropic.client.common.Role;
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
import static ai.agentscentral.anthropic.client.request.attributes.TextContentPart.TEXT;
import static ai.agentscentral.anthropic.client.request.attributes.ToolResultContentPart.TOOL_RESULT;
import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * Bidirectional converter between AgentJ {@link Message} types and Anthropic
 * {@link AnthropicMessage} wire types.
 *
 * <p>Outbound conversion ({@link #toAnthropicMessages}) maps each AgentJ message to the
 * appropriate Anthropic request format:
 * <ul>
 *   <li>{@link ai.agentscentral.core.session.message.UserMessage} → {@code user} role with
 *       text content parts</li>
 *   <li>{@link ai.agentscentral.core.session.message.AssistantMessage} → {@code assistant}
 *       role with text content parts</li>
 *   <li>{@link ai.agentscentral.core.session.message.ToolMessage} and
 *       {@link ai.agentscentral.core.session.message.HandOffMessage} → {@code user} role
 *       with {@code tool_result} content parts</li>
 * </ul>
 *
 * <p>Inbound conversion ({@link #toAssistantMessage}) maps a {@link MessagesResponse} back
 * to an {@link ai.agentscentral.core.session.message.AssistantMessage}, separating
 * {@link ai.agentscentral.anthropic.client.response.attributes.ToolUseResponseContent}
 * blocks into tool-call and handoff instruction lists based on whether the tool name
 * is found in the tools or handoffs map.</p>
 *
 * @author Rizwan Idrees
 */
class MessageConvertor {

    private final Map<String, ToolCall> tools;
    private final Map<String, Handoff> handOffs;

    /**
     * Creates a {@code MessageConvertor} scoped to the given tool and handoff maps.
     *
     * @param tools    AgentJ tool definitions keyed by name; used to classify
     *                 {@code tool_use} response blocks as tool calls
     * @param handOffs AgentJ handoff definitions keyed by handoff id; used to classify
     *                 {@code tool_use} response blocks as handoff instructions
     */
    public MessageConvertor(Map<String, ToolCall> tools,
                            Map<String, Handoff> handOffs) {
        this.tools = tools;
        this.handOffs = handOffs;
    }


    /**
     * Converts a list of AgentJ {@link Message}s to a list of
     * {@link AnthropicMessage}s suitable for the request body.
     *
     * @param messages the conversation history to convert
     * @return the converted list in the same order
     */
    List<AnthropicMessage> toAnthropicMessages(List<Message> messages) {
        return messages.stream().map(this::toAnthropicMessage).toList();
    }


    /**
     * Converts an Anthropic {@link MessagesResponse} to an AgentJ
     * {@link ai.agentscentral.core.session.message.AssistantMessage}.
     *
     * <p>Text and thinking blocks become {@link TextPart}s; tool-use blocks are split
     * into tool-call instructions (if the name matches a known tool) or handoff
     * instructions (if the name matches a known handoff).</p>
     *
     * @param contextId the conversation context the message belongs to
     * @param response  the raw API response to convert
     * @return the corresponding {@link ai.agentscentral.core.session.message.AssistantMessage}
     * @throws UnsupportedOperationException if the response role is not {@code assistant}
     */
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
                toolUse.input());
    }

    private HandoffInstruction toHandffInstruction(ToolUseResponseContent toolUse) {
        return new DefaultHandoffInstruction(toolUse.id(), handOffs.get(toolUse.name()));
    }

    private AnthropicMessage toAnthropicMessage(@Nonnull Message message) {

        return switch (message) {
            case UserMessage um -> new AnthropicUserMessage(Role.user, toTextMessageContent(um.parts()));
            case AssistantMessage am -> new AnthropicAssistantMessage(assistant, toTextMessageContent(am.parts()));
            case ToolMessage tm ->
                    new AnthropicUserMessage(Role.user, toToolResultMessageContent(tm.toolCallId(), tm.parts()));
            case HandOffMessage ho ->
                    new AnthropicUserMessage(Role.user, toToolResultMessageContent(ho.handOffId(), ho.parts()));
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
