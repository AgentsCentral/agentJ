package ai.agentscentral.core.handoff;

import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toMap;

/**
 * Indexes an agent's declared {@link Handoff} targets into a map keyed by handoff id.
 *
 * <p>Called at agent initialisation time by
 * {@link ai.agentscentral.core.agentic.executor.DefaultAgentExecutor} to produce the
 * map passed to the model provider. The provider uses the map to include handoff
 * definitions in the request, allowing the LLM to emit handoff instructions by id.</p>
 *
 * @author Rizwan Idrees
 */
public class HandoffsExtractor {

    /**
     * Creates a new {@code HandoffsExtractor}.
     */
    public HandoffsExtractor() {
    }

    /**
     * Converts a list of {@link Handoff} declarations into a map keyed by
     * {@link Handoff#id()}.
     *
     * @param handoffs the agent's declared handoff targets; may be {@code null} or empty
     * @return an unmodifiable map from handoff id to {@link Handoff}, or an empty map if
     *         {@code handoffs} is {@code null} or empty
     */
    public Map<String, Handoff> extractHandOffs(List<Handoff> handoffs) {

        if (isNull(handoffs) || handoffs.isEmpty()) {
            return Map.of();
        }

        return handoffs.stream().collect(toMap(Handoff::id, handoff -> handoff));
    }

}
