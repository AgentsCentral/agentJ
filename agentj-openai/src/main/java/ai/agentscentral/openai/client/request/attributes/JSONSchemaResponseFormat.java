package ai.agentscentral.openai.client.request.attributes;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSONSchemaResponseFormat
 * @param type
 * @param jsonSchema
 *
 * @author Rizwan Idrees
 */
public record JSONSchemaResponseFormat(String type,
                                       @JsonProperty("json_schema") String jsonSchema) implements ResponseFormat {

    private static final String JSON_SCHEMA = "json_schema";

    @Override
    public String type() {
        return JSON_SCHEMA;
    }

}
