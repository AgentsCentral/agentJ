package ai.agentscentral.core.session.id;

/**
 * DefaultSessionIdGenerator
 *
 * @author Rizwan Idrees
 */
public class DefaultSessionIdGenerator implements SessionIdGenerator {

    private static final String PREFIX = "session_";

    @Override
    public String generate() {
        return IdGenerator.generate(PREFIX);
    }

}
