package ai.agentscentral.core.session.id;

/**
 * Default implementation of {@link SessionIdGenerator} that produces identifiers of the
 * form {@code session_<uuid-without-hyphens>} (e.g.
 * {@code session_550e8400e29b41d4a716446655440000}).
 *
 * @author Rizwan Idrees
 */
public class DefaultSessionIdGenerator implements SessionIdGenerator {

    /**
     * Creates a new {@code DefaultSessionIdGenerator}.
     */
    public DefaultSessionIdGenerator() {
    }

    private static final String PREFIX = "session_";

    /**
     * {@inheritDoc}
     *
     * <p>Returns a {@code "session_"}-prefixed, hyphen-free UUID string.</p>
     */
    @Override
    public String generate() {
        return IdGenerator.generate(PREFIX);
    }

}
