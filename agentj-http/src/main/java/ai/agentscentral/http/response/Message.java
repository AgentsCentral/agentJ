package ai.agentscentral.http.response;

import ai.agentscentral.http.common.MessageType;
import ai.agentscentral.http.request.InterruptMessage;
import ai.agentscentral.http.request.TextMessage;
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

    String id();

    long timestamp();

}
