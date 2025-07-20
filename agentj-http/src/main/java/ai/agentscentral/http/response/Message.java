package ai.agentscentral.http.response;

public interface Message {

    MessageType type();
    String id();
    long timestamp();

}
