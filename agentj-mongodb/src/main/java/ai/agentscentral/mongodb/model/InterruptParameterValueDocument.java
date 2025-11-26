package ai.agentscentral.mongodb.model;

import ai.agentscentral.core.session.message.InterruptParameterValue;
import org.bson.Document;

/**
 * InterruptParameterValueDocument
 *
 * @author Mustafa Bhuiyan
 */
public class InterruptParameterValueDocument {
    private String name;
    private Document value;

    public InterruptParameterValueDocument() {
    }

    public InterruptParameterValueDocument(String name, Document value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Document getValue() {
        return value;
    }

    public void setValue(Document value) {
        this.value = value;
    }

    public static InterruptParameterValueDocument toInterruptParameterValueDocument(InterruptParameterValue interruptParameterValue) {
        Document valueDoc = new Document("value", interruptParameterValue.value());
        return new InterruptParameterValueDocument(interruptParameterValue.name(), valueDoc);
    }

    public static InterruptParameterValue toInterruptParameterValue(InterruptParameterValueDocument doc) {
        Object valueObj = doc.value != null ? doc.value.get("value") : null;
        return new InterruptParameterValue(doc.getName(), valueObj);
    }
}
