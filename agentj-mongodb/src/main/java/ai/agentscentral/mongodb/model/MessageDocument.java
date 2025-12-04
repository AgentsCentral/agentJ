package ai.agentscentral.mongodb.model;

import ai.agentscentral.mongodb.enums.MessageType;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * MessageDocument
 *
 * @author Mustafa Bhuiyan
 */
public class MessageDocument extends AgentJDocument {
    private MessageType messageType;

    // Common fields for all message types
    private String contextId;
    private String messageId;

    List<MessagePartDocument> messagePartsDocs;

    private Long timestamp;

    // AssistanceMessage specific fields
    private List<ToolCallInstructionDocument> toolCallDocs;
    private List<HandoffInstructionDocument> handoffDocs;

    // HandOffMessage specific fields
    private String handOffId;
    private String agentName;

    // ToolMessage specific fields
    private String toolCallId;
    private String toolName;

    // UserInterruptMessage specific fields
    private Map<String, String> interruptParameterValues;

    // No-args constructor
    public MessageDocument() {
    }

    // All-args constructor
    public MessageDocument(
            ObjectId id,
            MessageType messageType,
            String contextId,
            String messageId,
            List<MessagePartDocument> messagePartsDocs,
            Long timestamp,
            List<ToolCallInstructionDocument> toolCallDocs,
            List<HandoffInstructionDocument> handoffDocs,
            String handOffId,
            String agentName,
            String toolCallId,
            String toolName,
            Map<String, String> interruptParameterValues,
            Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.messageType = messageType;
        this.contextId = contextId;
        this.messageId = messageId;
        this.messagePartsDocs = messagePartsDocs;
        this.timestamp = timestamp;
        this.toolCallDocs = toolCallDocs;
        this.handoffDocs = handoffDocs;
        this.handOffId = handOffId;
        this.agentName = agentName;
        this.toolCallId = toolCallId;
        this.toolName = toolName;
        this.interruptParameterValues = interruptParameterValues;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

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

    public List<MessagePartDocument> getMessagePartsDocs() {
        return messagePartsDocs;
    }

    public void setMessagePartsDocs(List<MessagePartDocument> messagePartsDocs) {
        this.messagePartsDocs = messagePartsDocs;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public List<ToolCallInstructionDocument> getToolCallDocs() {
        return toolCallDocs;
    }

    public void setToolCallDocs(List<ToolCallInstructionDocument> toolCallDocs) {
        this.toolCallDocs = toolCallDocs;
    }

    public List<HandoffInstructionDocument> getHandoffDocs() {
        return handoffDocs;
    }

    public void setHandoffDocs(List<HandoffInstructionDocument> handoffDocs) {
        this.handoffDocs = handoffDocs;
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

    public Map<String, String> getInterruptParameterValues() {
        return interruptParameterValues;
    }

    public void setInterruptParameterValues(Map<String, String> interruptParameterValues) {
        this.interruptParameterValues = interruptParameterValues;
    }
}
