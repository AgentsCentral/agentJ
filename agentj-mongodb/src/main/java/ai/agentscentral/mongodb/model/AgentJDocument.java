package ai.agentscentral.mongodb.model;

import org.bson.types.ObjectId;

import java.time.Instant;

/**
 * AgentJDocument
 *
 * @author Mustafa Bhuiyan
 */
public abstract class AgentJDocument {
    protected ObjectId id;
    protected Instant createdAt;
    protected Instant updatedAt;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
