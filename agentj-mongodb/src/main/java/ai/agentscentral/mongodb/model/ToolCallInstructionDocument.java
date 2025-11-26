package ai.agentscentral.mongodb.model;

import ai.agentscentral.core.tool.DefaultToolCallInstruction;
import ai.agentscentral.core.tool.ToolCallInstruction;

import java.util.Map;

/**
 * ToolCallInstructionDocument
 *
 * @author Mustafa Bhuiyan
 */
public class ToolCallInstructionDocument {
    private ToolCallInstructionType type;
    private String id;
    private String name;
    private String rawArguments;
    private Map<String, Object> arguments;

    public ToolCallInstructionDocument() {
    }

    public ToolCallInstructionDocument(ToolCallInstructionType type, String id, String name, String rawArguments,
                                       Map<String, Object> arguments) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.rawArguments = rawArguments;
        this.arguments = arguments;
    }

    public ToolCallInstructionType getType() {
        return type;
    }

    public void setType(ToolCallInstructionType type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRawArguments() {
        return rawArguments;
    }

    public void setRawArguments(String rawArguments) {
        this.rawArguments = rawArguments;
    }

    public Map<String, Object> getArguments() {
        return arguments;
    }

    public void setArguments(Map<String, Object> arguments) {
        this.arguments = arguments;
    }

    // Factory method for DefaultToolCallInstruction
    private static ToolCallInstructionDocument toolCallInstructionDocument(DefaultToolCallInstruction instruction) {
        return new ToolCallInstructionDocument(ToolCallInstructionType.DEFAULT,
                instruction.id(), instruction.name(), instruction.rawArguments(), instruction.arguments());
    }

    public static ToolCallInstructionDocument toToolCallInstructionDoc(ToolCallInstruction toolCallInstruction) {
        return switch (toolCallInstruction) {
            case DefaultToolCallInstruction defaultToolCallInstruction -> toolCallInstructionDocument(defaultToolCallInstruction);
            default -> throw new IllegalArgumentException("Unsupported tool call instruction type: " + toolCallInstruction.getClass().getName());
        };
    }

    public static ToolCallInstruction toToolCallInstruction(ToolCallInstructionDocument doc) {
        return switch (doc.getType()) {
            case DEFAULT -> new DefaultToolCallInstruction(doc.getId(), doc.getName(), doc.getRawArguments(), doc.getArguments());
            default -> throw new IllegalArgumentException("Unsupported tool call instruction type: " + doc.getType());
        };
    }
}
