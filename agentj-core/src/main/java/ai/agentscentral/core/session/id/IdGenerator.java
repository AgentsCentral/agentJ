package ai.agentscentral.core.session.id;

import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.replaceEach;

/**
 * Package-private utility that generates prefixed, hyphen-free UUID-based identifiers.
 *
 * <p>Used by {@link DefaultSessionIdGenerator} and {@link DefaultMessageIdGenerator}
 * to produce unique identifiers of the form {@code <prefix><uuid-without-hyphens>},
 * e.g. {@code session_550e8400e29b41d4a716446655440000}.</p>
 *
 * @author Rizwan Idrees
 */
class IdGenerator {
    private static final String HYPHEN = "-";

    /**
     * Generates a unique identifier by prepending {@code prefix} to a randomly generated
     * UUID with all hyphens removed.
     *
     * @param prefix the string to prepend to the UUID; must not be {@code null}
     * @return a unique, hyphen-free, prefixed identifier
     */
    static String generate(String prefix){
        return prefix + replaceEach(randomUUID().toString(), new String[]{HYPHEN}, new String[]{EMPTY});
    }

}
