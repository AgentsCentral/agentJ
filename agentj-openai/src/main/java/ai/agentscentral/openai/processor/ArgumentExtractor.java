package ai.agentscentral.openai.processor;


import org.apache.commons.lang3.StringUtils;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * ArgumentExtractor
 *
 * @author Rizwan Idrees
 */
class ArgumentExtractor {

    private static final ObjectMapper mapper = new ObjectMapper();

    private ArgumentExtractor() {
    }

    static Map<String, Object> extractFromJson(String json) {
        if (StringUtils.isBlank(json)) {
            return Map.of();
        }
        try {
            return mapper.readValue(json, new TypeReference<>() {
            });
        } catch (JacksonException e) {
            return Map.of();
        }
    }

}
