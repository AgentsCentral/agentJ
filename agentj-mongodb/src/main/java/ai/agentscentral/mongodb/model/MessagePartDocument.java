package ai.agentscentral.mongodb.model;

import ai.agentscentral.core.session.message.InterruptParameterValue;
import ai.agentscentral.core.session.message.InterruptPreCallResult;
import ai.agentscentral.core.session.message.MessagePart;
import ai.agentscentral.core.session.message.MessagePartType;
import ai.agentscentral.core.session.message.TextPart;
import ai.agentscentral.core.session.message.ToolInterruptParameter;
import ai.agentscentral.core.session.message.ToolInterruptPart;
import ai.agentscentral.core.session.message.UserInterruptPart;

import java.util.List;
import java.util.Map;

import static ai.agentscentral.core.session.message.MessagePartType.user_interrupt;

/**
 * MessagePartDocument
 *
 * @author Mustafa Bhuiyan
 */
public class MessagePartDocument {
    private MessagePartType type;

    // text part
    private String text;

    // tool interrupt part
    private String toolCallId;
    private String interruptName;
    private String renderer;
    private Map<String,Object> toolCallParameters;
    private List<InterruptPreCallResultDocument> preCallResultDocs;
    private List<ToolInterruptParameter> interruptParameters;

    // user interrupt part
    private List<InterruptParameterValueDocument> userInterruptParameterDocs;

    public MessagePartDocument(MessagePartType type, String text, String toolCallId, String interruptName,
                               String renderer, Map<String, Object> toolCallParameters,
                               List<InterruptPreCallResultDocument> preCallResultDocs,
                               List<ToolInterruptParameter> interruptParameters,
                               List<InterruptParameterValueDocument> userInterruptParameterDocs) {
        this.type = type;
        this.text = text;
        this.toolCallId = toolCallId;
        this.interruptName = interruptName;
        this.renderer = renderer;
        this.toolCallParameters = toolCallParameters;
        this.preCallResultDocs = preCallResultDocs;
        this.interruptParameters = interruptParameters;
        this.userInterruptParameterDocs = userInterruptParameterDocs;
    }

    public MessagePartDocument() {
    }

    public MessagePartType getType() {
        return type;
    }

    public void setType(MessagePartType type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getToolCallId() {
        return toolCallId;
    }

    public void setToolCallId(String toolCallId) {
        this.toolCallId = toolCallId;
    }

    public String getInterruptName() {
        return interruptName;
    }

    public void setInterruptName(String interruptName) {
        this.interruptName = interruptName;
    }

    public String getRenderer() {
        return renderer;
    }

    public void setRenderer(String renderer) {
        this.renderer = renderer;
    }

    public Map<String, Object> getToolCallParameters() {
        return toolCallParameters;
    }

    public void setToolCallParameters(Map<String, Object> toolCallParameters) {
        this.toolCallParameters = toolCallParameters;
    }

    public List<InterruptPreCallResultDocument> getPreCallResultDocs() {
        return preCallResultDocs;
    }

    public void setPreCallResultDocs(List<InterruptPreCallResultDocument> preCallResultDocs) {
        this.preCallResultDocs = preCallResultDocs;
    }

    public List<ToolInterruptParameter> getInterruptParameters() {
        return interruptParameters;
    }

    public void setInterruptParameters(List<ToolInterruptParameter> interruptParameters) {
        this.interruptParameters = interruptParameters;
    }

    public List<InterruptParameterValueDocument> getUserInterruptParameterDocs() {
        return userInterruptParameterDocs;
    }

    public void setUserInterruptParameterDocs(List<InterruptParameterValueDocument> userInterruptParameterDocs) {
        this.userInterruptParameterDocs = userInterruptParameterDocs;
    }

    private static MessagePartDocument toTextPartDoc(TextPart textPart) {
        return new MessagePartDocument(MessagePartType.text, textPart.text(),
                null, null, null, null, null, null, null);
    }

    private static List<InterruptPreCallResultDocument> toInterruptPreCallResultDocument(List<InterruptPreCallResult> interruptPreCallResults) {
        return interruptPreCallResults.stream()
                .map(InterruptPreCallResultDocument::toInterruptPreCallResultDocument)
                .toList();
    }

    // Factory for ToolInterruptPart
    private static MessagePartDocument toToolInterruptPartDoc(ToolInterruptPart toolInterruptPart) {
        return new MessagePartDocument(MessagePartType.tool_interrupt, null, toolInterruptPart.toolCallId(),
                toolInterruptPart.interruptName(), toolInterruptPart.renderer(), toolInterruptPart.toolCallParameters(),
                toInterruptPreCallResultDocument(toolInterruptPart.preCallResults()),
                toolInterruptPart.interruptParameters(), null);
    }

    private static List<InterruptParameterValueDocument> toInterruptParameterValueDocument(List<InterruptParameterValue> interruptParameterValues) {
        return interruptParameterValues.stream()
                .map(InterruptParameterValueDocument::toInterruptParameterValueDocument)
                .toList();
    }

    // Factory for UserInterruptPart
    private static MessagePartDocument toUserInterruptPartDoc(UserInterruptPart userInterruptPart) {
        return new MessagePartDocument(user_interrupt, null,
                userInterruptPart.toolCallId(), userInterruptPart.interruptName(), null, null,
                null, null, toInterruptParameterValueDocument(userInterruptPart.interruptParameters()));
    }

    public static MessagePartDocument toMessagePartDoc(MessagePart messagePart) {
        return switch (messagePart.type()) {
            case text -> toTextPartDoc((TextPart) messagePart);
            case tool_interrupt -> toToolInterruptPartDoc((ToolInterruptPart) messagePart);
            case user_interrupt -> toUserInterruptPartDoc((UserInterruptPart) messagePart);
            default -> throw new IllegalArgumentException("Unsupported message part type: " + messagePart.type());
        };
    }

    private static List<InterruptPreCallResult> toInterruptPreCallResult(List<InterruptPreCallResultDocument> interruptPreCallResultDocs) {
        return interruptPreCallResultDocs.stream()
                .map(InterruptPreCallResultDocument::toInterruptPreCallResult)
                .toList();
    }

    private static List<InterruptParameterValue> toInterruptParameterValue(List<InterruptParameterValueDocument> interruptParameterValueDocs) {
        return interruptParameterValueDocs.stream()
                .map(InterruptParameterValueDocument::toInterruptParameterValue)
                .toList();
    }

    public static MessagePart toMessagePart(MessagePartDocument doc) {
        return switch (doc.getType()) {
            case text -> new TextPart(doc.getType(), doc.getText());
            case tool_interrupt -> new ToolInterruptPart(doc.getType(), doc.getToolCallId(), doc.getInterruptName(),
                    doc.getRenderer(), doc.getToolCallParameters(),
                    toInterruptPreCallResult(doc.getPreCallResultDocs()), doc.getInterruptParameters());
            case user_interrupt -> new UserInterruptPart(doc.getType(), doc.getToolCallId(), doc.getInterruptName(),
                    toInterruptParameterValue(doc.getUserInterruptParameterDocs()));
            default -> throw new IllegalArgumentException("Unsupported message part type: " + doc.getType());
        };
    }
}
