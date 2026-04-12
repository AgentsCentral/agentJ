package ai.agentscentral.http.common;

/**
 * Discriminator enum for the type of a message in the AgentJ HTTP request/response
 * wire format.
 *
 * <p>Used as the {@code type} field in both inbound
 * ({@link ai.agentscentral.http.request.Message}) and outbound
 * ({@link ai.agentscentral.http.response.Message}) message types to distinguish plain
 * text messages from tool-interrupt messages.</p>
 *
 * @author Rizwan Idrees
 */
public enum MessageType {
    /** A plain text message from the user or assistant. */
    text,
    /** A tool-call interrupt requiring user input before execution can proceed. */
    interrupt
}
