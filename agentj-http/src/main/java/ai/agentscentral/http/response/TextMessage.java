package ai.agentscentral.http.response;

public record TextMessage(MessageType type, String id, String text, long timestamp) implements Message {

    public MessageType type() {
        return MessageType.text;
    }
}
