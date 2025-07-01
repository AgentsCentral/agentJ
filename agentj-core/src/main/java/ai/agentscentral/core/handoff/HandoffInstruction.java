package ai.agentscentral.core.handoff;

/**
 * HandoffInstruction
 *
 *
 * @author Rizwan Idrees
 */
public interface HandoffInstruction {

    String callId();
    Handoff handoff();
}
