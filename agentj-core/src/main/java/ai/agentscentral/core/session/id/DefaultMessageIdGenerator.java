package ai.agentscentral.core.session.id;

/**
 * DefaultMessageIdGenerator
 *
 * @author Rizwan Idrees
 */
public class DefaultMessageIdGenerator implements MessageIdGenerator {

    private static final MessageIdGenerator instance = new DefaultMessageIdGenerator();

    private static final String PREFIX = "msg_";

    private DefaultMessageIdGenerator() {
    }

    @Override
    public String generate() {
        return IdGenerator.generate(PREFIX);
    }

    public static MessageIdGenerator getInstance() {
        return instance;
    }

}
