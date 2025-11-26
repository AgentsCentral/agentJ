package ai.agentscentral.mongodb.model;

import ai.agentscentral.core.handoff.DefaultHandoffInstruction;
import ai.agentscentral.core.handoff.Handoff;
import ai.agentscentral.core.handoff.HandoffInstruction;


/**
 * HandoffInstructionDocument
 *
 * @author Mustafa Bhuiyan
 */
public class HandoffInstructionDocument {
    private HandoffInstructionType type;
    // Fields for DefaultHandoffInstruction
    private String callId;
    private Handoff handoff;

    public HandoffInstructionDocument() {
    }

    public HandoffInstructionDocument(HandoffInstructionType type, String callId, Handoff handoff) {
        this.type = type;
        this.callId = callId;
        this.handoff = handoff;
    }

    public HandoffInstructionType getType() {
        return type;
    }

    public void setType(HandoffInstructionType type) {
        this.type = type;
    }

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public Handoff getHandoff() {
        return handoff;
    }

    public void setHandoff(Handoff handoff) {
        this.handoff = handoff;
    }

    // Factory method for DefaultHandoffInstruction
    private static HandoffInstructionDocument toHandoffInstructionDocument(
            DefaultHandoffInstruction handoffInstruction) {
        return new HandoffInstructionDocument(HandoffInstructionType.DEFAULT, handoffInstruction.callId(),
                handoffInstruction.handoff());
    }

    public static HandoffInstructionDocument toHandoffInstructionDoc(HandoffInstruction handoffInstruction) {
        return switch (handoffInstruction) {
            case DefaultHandoffInstruction defaultHandoffInstruction -> toHandoffInstructionDocument(defaultHandoffInstruction);
            default -> throw new IllegalArgumentException("Unsupported handoff instruction type: " +
                    handoffInstruction.getClass().getName());
        };
    }

    public static HandoffInstruction toHandoffInstruction(HandoffInstructionDocument doc) {
        return switch (doc.getType()) {
            case DEFAULT -> new DefaultHandoffInstruction(doc.getCallId(), doc.getHandoff());
            default -> throw new IllegalArgumentException("Unsupported handoff instruction type: " + doc.getType());
        };
    }
}
