package ai.agentscentral.openai.processor;

import ai.agentscentral.core.handoff.DefaultHandoffInstruction;
import ai.agentscentral.core.handoff.Handoff;
import ai.agentscentral.core.handoff.HandoffInstruction;
import ai.agentscentral.core.session.message.*;
import ai.agentscentral.core.tool.DefaultToolCallInstruction;
import ai.agentscentral.core.tool.ToolCallInstruction;
import ai.agentscentral.openai.client.request.attributes.*;
import ai.agentscentral.openai.client.response.parameters.ChoiceMessage;
import ai.agentscentral.openai.client.response.parameters.Function;
import ai.agentscentral.openai.client.response.parameters.ToolCall;
import jakarta.annotation.Nonnull;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static ai.agentscentral.core.session.message.MessagePartType.text;
import static ai.agentscentral.openai.client.request.attributes.OpenAIAssistantMessage.ASSISTANT;
import static ai.agentscentral.openai.client.request.attributes.OpenAIDeveloperMessage.DEVELOPER;
import static ai.agentscentral.openai.client.request.attributes.OpenAIToolMessage.TOOL;
import static ai.agentscentral.openai.client.request.attributes.OpenAIUserMessage.USER;
import static ai.agentscentral.openai.client.request.attributes.TextContentPart.TEXT;
import static ai.agentscentral.openai.processor.ArgumentExtractor.extractFromJson;
import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * MessageConvertor
 *
 * @author Rizwan Idrees
 */
class MessageConvertor {

    private final Map<String, ai.agentscentral.core.tool.ToolCall> tools;
    private final Map<String, Handoff> handOffs;

    public MessageConvertor(Map<String, ai.agentscentral.core.tool.ToolCall> tools,
                            Map<String, Handoff> handOffs) {
        this.tools = tools;
        this.handOffs = handOffs;
    }

    List<OpenAIMessage> toOpenAIMessages(DeveloperMessage message, List<Message> messages) {

        final List<OpenAIMessage> context = messages.stream()
                .filter(m -> !isInterruptMessage(m))
                .map(this::toOpenAIMessage).toList();

        final OpenAIMessage agentContext = toOpenAIMessage(message);
        final List<OpenAIMessage> fullContext = new ArrayList<>();

        fullContext.add(agentContext);
        fullContext.addAll(context);

        return fullContext;
    }

    AssistantMessage toAssistantMessage(String contextId, String responseId, List<ChoiceMessage> messages, Long created) {
        final List<ChoiceMessage> assistantMessages = messages.stream().filter(m -> m.role().equals("assistant"))
                .toList();

        final List<TextPart> textParts = assistantMessages.stream().map(ChoiceMessage::content).filter(StringUtils::isNotBlank)
                .map(c -> new TextPart(text, c)).toList();

        final TextPart[] parts = textParts.isEmpty() ? null : textParts.toArray(new TextPart[0]);

        final List<ToolCall> choiceToolCalls = assistantMessages.stream().filter(ChoiceMessage::hasToolCalls)
                .flatMap(m -> m.toolCalls().stream())
                .toList();

        final List<ToolCallInstruction> toolCalls = choiceToolCalls.isEmpty() ?
                List.of() : toToolCallInstructions(choiceToolCalls);

        final List<HandoffInstruction> handoffCalls = choiceToolCalls.isEmpty() ?
                List.of() : toHandOffs(choiceToolCalls);

        return new AssistantMessage(contextId, responseId, parts, toolCalls, handoffCalls, System.currentTimeMillis());
    }

    private List<ToolCallInstruction> toToolCallInstructions(List<ToolCall> toolCalls) {
        return toolCalls.stream()
                .filter(toolCall -> tools.containsKey(toolCall.function().name()))
                .map(this::toToolCallInstruction).toList();
    }

    private List<HandoffInstruction> toHandOffs(List<ToolCall> toolCalls) {
        return toolCalls.stream()
                .filter(toolCall -> handOffs.containsKey(toolCall.function().name()))
                .map(this::toHandffInstruction)
                .toList();
    }

    private ToolCallInstruction toToolCallInstruction(ToolCall toolCall) {
        final Function function = toolCall.function();
        final String toolName = function.name();
        return new DefaultToolCallInstruction(toolCall.id(),
                toolName,
                function.arguments(),
                extractFromJson(function.arguments()),
                tools.get(toolName));
    }

    private HandoffInstruction toHandffInstruction(ToolCall toolCall) {
        return new DefaultHandoffInstruction(toolCall.id(), handOffs.get(toolCall.function().name()));
    }

    private OpenAIMessage toOpenAIMessage(@Nonnull Message message) {

        return switch (message) {
            case DeveloperMessage dm -> new OpenAIDeveloperMessage(DEVELOPER, null, toMessageContent(dm.parts()));
            case UserMessage um -> new OpenAIUserMessage(USER, null, toMessageContent(um.parts()));
            case AssistantMessage am -> new OpenAIAssistantMessage(ASSISTANT, toMessageContent(am.parts()), null,
                    null, toolCalls(am));
            case ToolMessage tm -> new OpenAIToolMessage(TOOL, tm.toolCallId(), toMessageContent(tm.parts()));
            case HandOffMessage ho -> new OpenAIToolMessage(TOOL, ho.handOffId(), toMessageContent(ho.parts()));
            default -> throw new RuntimeException("Cannot convert message of type " + message.getClass());
        };
    }

    private MessageContents toMessageContent(MessagePart[] parts) {

        if (Objects.isNull(parts) || parts.length == 0) {
            return new MessageContents(List.of(new TextContentPart(TEXT, EMPTY)));
        }

        final List<TextContentPart> textContentParts = Stream.of(parts).filter(p -> p instanceof TextPart)
                .map(p -> (TextPart) p)
                .map(p -> new TextContentPart(TEXT, p.text())).toList();

        return new MessageContents(textContentParts);
    }

    private List<OpenAIToolCall> toolCalls(AssistantMessage assistantMessage) {

        if (assistantMessage.hasToolCalls()) {
            return assistantMessage.toolCalls().stream().map(OpenAIToolConvertor::toOpenAIToolCall).toList();
        } else if (assistantMessage.hasHandOffs()) {
            return assistantMessage.handoffs().stream().map(OpenAIToolConvertor::toOpenAIToolCall).toList();
        }

        return null;
    }

    private boolean isInterruptMessage(Message message){
        if(message instanceof ToolInterruptMessage || message instanceof UserInterruptMessage){
            return true;
        }
        else if(message instanceof  UserMessage um){
            return Objects.isNull(um.parts()) || Stream.of(um.parts())
                    .allMatch(p -> p instanceof ToolInterruptPart || p instanceof UserInterruptPart);
        }
        return false;
    }

}
