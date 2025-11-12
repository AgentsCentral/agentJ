package ai.agentscentral.mongodb.model;

import ai.agentscentral.core.handoff.HandoffInstruction;
import ai.agentscentral.core.session.message.MessageType;
import ai.agentscentral.core.session.message.TextPart;
import ai.agentscentral.core.tool.ToolCallInstruction;

import java.util.List;

public class MessageDocument {
    // Common fields for all message types
    private String contextId;
    private String messageId;
    private MessageType type;
    private List<TextPart> textParts;
    private Long timestamp;

    // AssistanceMessage specific fields
    private List<ToolCallInstruction> toolCalls;
    private List<HandoffInstruction> handoffs;

    // HandOffMessage specific fields
    private String handOffId;
    private String agentName;

    // ToolMessage specific fields
    private String toolCallId;
    private String toolName;

    // No-args constructor
    public MessageDocument() {
    }

    // All-args constructor
    public MessageDocument(
            String contextId,
            String messageId,
            MessageType type,
            List<TextPart> textParts,
            Long timestamp,
            List<ToolCallInstruction> toolCalls,
            List<HandoffInstruction> handoffs,
            String handOffId,
            String agentName,
            String toolCallId,
            String toolName) {
        this.contextId = contextId;
        this.messageId = messageId;
        this.type = type;
        this.textParts = textParts;
        this.timestamp = timestamp;
        this.toolCalls = toolCalls;
        this.handoffs = handoffs;
        this.handOffId = handOffId;
        this.agentName = agentName;
        this.toolCallId = toolCallId;
        this.toolName = toolName;
    }

    // Getters and Setters
    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public List<TextPart> getTextParts() {
        return textParts;
    }

    public void setTextParts(List<TextPart> textParts) {
        this.textParts = textParts;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public List<ToolCallInstruction> getToolCalls() {
        return toolCalls;
    }

    public void setToolCalls(List<ToolCallInstruction> toolCalls) {
        this.toolCalls = toolCalls;
    }

    public List<HandoffInstruction> getHandoffs() {
        return handoffs;
    }

    public void setHandoffs(List<HandoffInstruction> handoffs) {
        this.handoffs = handoffs;
    }

    public String getHandOffId() {
        return handOffId;
    }

    public void setHandOffId(String handOffId) {
        this.handOffId = handOffId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getToolCallId() {
        return toolCallId;
    }

    public void setToolCallId(String toolCallId) {
        this.toolCallId = toolCallId;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

}
