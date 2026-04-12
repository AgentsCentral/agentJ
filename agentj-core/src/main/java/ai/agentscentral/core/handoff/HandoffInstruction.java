package ai.agentscentral.core.handoff;

/**
 * Represents a handoff instruction emitted by the model provider within an assistant
 * message, indicating that execution should be transferred to another agentic entity.
 *
 * <p>When a model response contains a handoff, the provider layer parses it into a
 * {@code HandoffInstruction} and attaches it to the
 * {@link ai.agentscentral.core.session.message.AssistantMessage}. The
 * {@link ai.agentscentral.core.agentic.executor.DefaultAgentExecutor} then passes it to
 * {@link ai.agentscentral.core.agentic.executor.HandoffExecutor#handoff} to resolve the
 * target and switch the active executor.</p>
 *
 * <p>The default implementation is {@link DefaultHandoffInstruction}.</p>
 *
 * @author Rizwan Idrees
 */
public interface HandoffInstruction {

    /**
     * Returns the call identifier assigned by the model provider to this handoff invocation.
     *
     * <p>Used to correlate the instruction with the corresponding
     * {@link ai.agentscentral.core.session.message.HandOffMessage} recorded in the context.</p>
     *
     * @return the call id; never {@code null}
     */
    String callId();

    /**
     * Returns the {@link Handoff} descriptor that defines the target agentic entity.
     *
     * @return the handoff definition; never {@code null}
     */
    Handoff handoff();
}
