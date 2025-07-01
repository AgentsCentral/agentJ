package ai.agentscentral.core.handoff;

import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toMap;

/**
 * HandoffsExtractor
 *
 * @author Rizwan Idrees
 */
public class HandoffsExtractor {

    private static final HandoffsExtractor instance = new HandoffsExtractor();


    private HandoffsExtractor() {

    }

    public Map<String, Handoff> extractHandOffs(List<Handoff> handoffs) {

        if (isNull(handoffs) || handoffs.isEmpty()) {
            return Map.of();
        }

        return handoffs.stream().collect(toMap(Handoff::id, handoff -> handoff));
    }



    public static HandoffsExtractor getInstance() {
        return instance;
    }


}
