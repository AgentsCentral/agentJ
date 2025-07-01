package ai.agentscentral.core.conversation;

/**
 * DefaultConversationIdGenerator
 *
 * @author Rizwan Idrees
 */
public class DefaultConversationIdGenerator implements ConversationIdGenerator {

    private static final String PREFIX = "cv_";

    @Override
    public String generate() {
        return IdGenerator.generate(PREFIX);
    }

}
