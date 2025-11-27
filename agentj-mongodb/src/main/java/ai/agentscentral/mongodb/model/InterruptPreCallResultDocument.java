package ai.agentscentral.mongodb.model;

import ai.agentscentral.core.session.message.InterruptPreCallResult;
import org.bson.Document;

/**
 * InterruptPreCallResultDocument
 *
 * @author Mustafa Bhuiyan
 */
public class InterruptPreCallResultDocument {
    private String name;
    private Document result;

    public InterruptPreCallResultDocument() {
    }

    public InterruptPreCallResultDocument(String name, Document result) {
        this.name = name;
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Document getResult() {
        return result;
    }

    public void setResult(Document result) {
        this.result = result;
    }

    public static InterruptPreCallResultDocument toInterruptPreCallResultDocument(InterruptPreCallResult interruptPreCallResult) {
        Document resultDoc = new Document("result", interruptPreCallResult.result());
        return new InterruptPreCallResultDocument(interruptPreCallResult.name(), resultDoc);
    }

    public static InterruptPreCallResult toInterruptPreCallResult(InterruptPreCallResultDocument doc) {
        Object resultObj = doc.result != null ? doc.result.get("result") : null;
        return new InterruptPreCallResult(doc.getName(), resultObj);
    }
}
