package ai.agentscentral.core.session.id;

/**
 * Default implementation of {@link MessageIdGenerator} that produces identifiers of the
 * form {@code msg_<uuid-without-hyphens>} (e.g.
 * {@code msg_550e8400e29b41d4a716446655440000}).
 *
 * @author Rizwan Idrees
 */
public class DefaultMessageIdGenerator implements MessageIdGenerator {

    private static final String PREFIX = "msg_";

    /**
     * {@inheritDoc}
     *
     * <p>Returns a {@code "msg_"}-prefixed, hyphen-free UUID string.</p>
     */
    @Override
    public String generate() {
        return IdGenerator.generate(PREFIX);
    }

}
