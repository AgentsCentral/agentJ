package ai.agentscentral.openai.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

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
        } catch (JsonProcessingException e) {
            return Map.of();
        }
    }

}
