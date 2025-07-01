package ai.agentscentral.core.conversation;

/**
 * DefaultMessageIdGenerator
 *
 * @author Rizwan Idrees
 */
public class DefaultMessageIdGenerator implements MessageIdGenerator {


    private static final String PREFIX = "msg_";

    @Override
    public String generate() {
        return IdGenerator.generate(PREFIX);
    }

}
