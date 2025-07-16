package ai.agentscentral.core.session.id;

/**
 * DefaultSessionIdGenerator
 *
 * @author Rizwan Idrees
 */
public class DefaultSessionIdGenerator implements SessionIdGenerator {

    private static final SessionIdGenerator instance = new DefaultSessionIdGenerator();
    private static final String PREFIX = "session_";

    private DefaultSessionIdGenerator() {
    }

    @Override
    public String generate() {
        return IdGenerator.generate(PREFIX);
    }

    public static SessionIdGenerator getInstance() {
        return instance;
    }
}
