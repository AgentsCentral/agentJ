package ai.agentscentral.openai.client.request.attributes;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * {@link ResponseFormat} that instructs the model to produce structured JSON output
 * constrained by a JSON Schema.
 *
 * <p>The {@code type} component always returns {@code "json_schema"} via the
 * {@link #type()} override.</p>
 *
 * @param type       always {@code "json_schema"}
 * @param jsonSchema {@code json_schema} — the schema definition string
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
