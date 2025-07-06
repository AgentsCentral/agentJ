//package ai.agentscentral.anthropic.processor;
//
//import ai.agentscentral.anthropic.client.request.attributes.AnthropicMessage;
//import ai.agentscentral.anthropic.client.request.attributes.MessageContent;
//import ai.agentscentral.anthropic.client.request.attributes.TextContent;
//import ai.agentscentral.core.conversation.message.*;
//import ai.agentscentral.core.handoff.DefaultHandoffInstruction;
//import ai.agentscentral.core.handoff.Handoff;
//import ai.agentscentral.core.handoff.HandoffInstruction;
//import ai.agentscentral.core.tool.DefaultToolCallInstruction;
//import ai.agentscentral.core.tool.ToolCallInstruction;
//import jakarta.annotation.Nonnull;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.stream.Stream;
//
//import static ai.agentscentral.core.conversation.message.MessagePartType.text;
//import static java.util.stream.Collectors.joining;
//import static org.apache.commons.lang3.StringUtils.EMPTY;
//import static org.apache.commons.lang3.StringUtils.isBlank;
//
///**
// * MessageConvertor
// *
// * @author Rizwan Idrees
// */
//class MessageConvertor {
//
//    private final Map<String, ai.agentscentral.core.tool.ToolCall> tools;
//    private final Map<String, Handoff> handOffs;
//
//    public MessageConvertor(Map<String, ai.agentscentral.core.tool.ToolCall> tools,
//                            Map<String, Handoff> handOffs) {
//        this.tools = tools;
//        this.handOffs = handOffs;
//    }
//
//
//    List<AnthropicMessage> toAnthropicMessages(DeveloperMessage message, List<Message> messages) {
//        final List<AnthropicMessage> context = messages.stream().map(this::toOpenAIMessage).toList();
//        final AnthropicMessage agentContext = toOpenAIMessage(message);
//        final List<OpenAIMessage> fullContext = new ArrayList<>();
//
//        fullContext.add(agentContext);
//        fullContext.addAll(context);
//
//        return fullContext;
//    }
//
//
//    AssistantMessage toAssistantMessage(String contextId, String responseId, ChoiceMessage message, Long created) {
//
//        if (!"assistant".equals(message.role())) {
//            throw new UnsupportedOperationException("Message for role " + message.role() + "is not support");
//        }
//
//        final TextPart[] parts = isBlank(message.content()) ? null : new TextPart[]{new TextPart(text, message.content())};
//        final List<ToolCallInstruction> toolCalls = message.hasToolCalls() ? toToolCallInstructions(message.toolCalls()) : List.of();
//        final List<HandoffInstruction> handoffCalls = message.hasToolCalls() ? toHandOffs(message.toolCalls()) : List.of();
//        return new AssistantMessage(contextId, responseId, parts, toolCalls, handoffCalls, System.currentTimeMillis());
//    }
//
//    private List<ToolCallInstruction> toToolCallInstructions(List<ToolCall> toolCalls) {
//        return toolCalls.stream()
//                .filter(toolCall -> tools.containsKey(toolCall.function().name()))
//                .map(this::toToolCallInstruction).toList();
//    }
//
//    private List<HandoffInstruction> toHandOffs(List<ToolCall> toolCalls) {
//        return toolCalls.stream()
//                .filter(toolCall -> handOffs.containsKey(toolCall.function().name()))
//                .map(this::toHandffInstruction)
//                .toList();
//    }
//
//    private ToolCallInstruction toToolCallInstruction(ToolCall toolCall) {
//        final Function function = toolCall.function();
//        final String toolName = function.name();
//        return new DefaultToolCallInstruction(toolCall.id(),
//                toolName,
//                function.arguments(),
//                extractFromJson(function.arguments()),
//                tools.get(toolName));
//    }
//
//    private HandoffInstruction toHandffInstruction(ToolCall toolCall) {
//        return new DefaultHandoffInstruction(toolCall.id(), handOffs.get(toolCall.function().name()));
//    }
//    private AnthropicMessage toAnthropicMessage(@Nonnull Message message) {
//
//        return switch (message) {
//            case DeveloperMessage dm ->  new OpenAIDeveloperMessage(DEVELOPER, null, toMessageContent(dm.parts()));
//            case UserMessage um -> new OpenAIUserMessage(USER, null, toMessageContent(um.parts()));
//            case AssistantMessage am -> new OpenAIAssistantMessage(ASSISTANT, toMessageContent(am.parts()), null,
//                    null, toolCalls(am));
//            case ToolMessage tm -> new OpenAIToolMessage(TOOL, tm.toolCallId(), toMessageContent(tm.parts()));
//            case HandOffMessage ho -> new OpenAIToolMessage(TOOL, ho.handOffId(), toMessageContent(ho.parts()));
//            default -> throw new RuntimeException("Cannot convert message of type " + message.getClass());
//        };
//    }
//
//    private MessageContent toMessageContent(MessagePart[] parts) {
//        if (Objects.isNull(parts)) {
//            return new TextContent(EMPTY);
//        }
//
//        if (parts instanceof TextPart[] textParts) {
//            String text = Stream.of(textParts).map(TextPart::text).collect(joining());
//            return new TextContent(text);
//        }
//
//        return new TextContent(EMPTY);
//    }
//
//    private List<OpenAIToolCall> toolCalls(AssistantMessage assistantMessage) {
//
//        if (assistantMessage.hasToolCalls()) {
//            return assistantMessage.toolCalls().stream().map(OpenAIToolConvertor::toOpenAIToolCall).toList();
//        } else if (assistantMessage.hasHandOffs()) {
//            return assistantMessage.handoffs().stream().map(OpenAIToolConvertor::toOpenAIToolCall).toList();
//        }
//
//        return null;
//    }
//
//
//}
