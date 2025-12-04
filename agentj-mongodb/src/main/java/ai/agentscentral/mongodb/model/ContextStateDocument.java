package ai.agentscentral.mongodb.model;

import ai.agentscentral.mongodb.enums.ContextStateType;
import org.bson.types.ObjectId;

import java.time.Instant;

/**
 * ContextStateDocument
 *
 * @author Mustafa Bhuiyan
 */
public class ContextStateDocument extends AgentJDocument {
    private ContextStateType type;
    private String contextId;
    private String currentTeam;
    private String currentAgent;
    private String partOfTeam;

    public ContextStateDocument() {}

    public ContextStateDocument(ObjectId id, ContextStateType type, String contextId, String currentTeam,
                                String currentAgent, String partOfTeam, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.type = type;
        this.contextId = contextId;
        this.currentTeam = currentTeam;
        this.currentAgent = currentAgent;
        this.partOfTeam = partOfTeam;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ContextStateType getType() {
        return type;
    }

    public void setType(ContextStateType type) {
        this.type = type;
    }

    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    public String getCurrentTeam() {
        return currentTeam;
    }

    public void setCurrentTeam(String currentTeam) {
        this.currentTeam = currentTeam;
    }

    public String getCurrentAgent() {
        return currentAgent;
    }

    public void setCurrentAgent(String currentAgent) {
        this.currentAgent = currentAgent;
    }

    public String getPartOfTeam() {
        return partOfTeam;
    }

    public void setPartOfTeam(String partOfTeam) {
        this.partOfTeam = partOfTeam;
    }
}
