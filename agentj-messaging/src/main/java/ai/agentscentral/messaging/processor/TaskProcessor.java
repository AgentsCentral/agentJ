package ai.agentscentral.messaging.processor;

import ai.agentscentral.messaging.message.TaskMessage;
import jakarta.annotation.Nonnull;

/**
 * TaskProcessor
 *
 * @author Rizwan Idrees
 */
public interface TaskProcessor {


    void process(@Nonnull TaskMessage message);
}
