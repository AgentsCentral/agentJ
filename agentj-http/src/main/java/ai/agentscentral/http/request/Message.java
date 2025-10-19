package ai.agentscentral.http.request;

import ai.agentscentral.http.common.MessageType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Message
 *
 * @author Rizwan Idrees
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TextMessage.class, name = "text"),
        @JsonSubTypes.Type(value = InterruptMessage.class, name = "interrupt"),
})
public interface Message {

    MessageType type();
}
