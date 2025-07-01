package ai.agentscentral.core.conversation;

import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.replaceEach;

/**
 * IdGenerator
 *
 * @author Rizwan Idrees
 */
class IdGenerator {
    private static final String HYPHEN = "-";
    static String generate(String prefix){
        return prefix + replaceEach(randomUUID().toString(), new String[]{HYPHEN}, new String[]{EMPTY});
    }

}
