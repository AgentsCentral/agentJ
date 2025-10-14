package ai.agentscentral.http.response;

public record InterruptMessage(MessageType type,
                               String id,
                               InterruptPart[] interrupt,
                               long timestamp) implements Message {

    public MessageType type() {
        return MessageType.interrupt;
    }
}
