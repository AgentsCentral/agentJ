package ai.agentscentral.core.factory;

import ai.agentscentral.core.agentic.executor.register.DefaultInterruptPreCallRegistrar;
import ai.agentscentral.core.handoff.HandoffsExtractor;
import ai.agentscentral.core.session.id.DefaultMessageIdGenerator;
import ai.agentscentral.core.session.id.DefaultSessionIdGenerator;
import ai.agentscentral.core.session.id.MessageIdGenerator;
import ai.agentscentral.core.session.id.SessionIdGenerator;
import ai.agentscentral.core.session.message.ToolMessage;
import ai.agentscentral.core.tool.DefaultToolCallExecutor;
import ai.agentscentral.core.tool.ToolBagToolsExtractor;
import ai.agentscentral.core.tool.ToolCallExecutor;

/**
 * AgentJFactory
 *
 * @author Rizwan Idrees
 */
public interface AgentJFactory {


    default ToolBagToolsExtractor getToolBagToolsExtractor() {
        return ToolBagToolsExtractor.getInstance();
    }

    default ToolCallExecutor<ToolMessage> getToolCallExecutor() {
        return new DefaultToolCallExecutor<>(new DefaultInterruptPreCallRegistrar());
    }

    default HandoffsExtractor getHandoffsExtractor() {
        return HandoffsExtractor.getInstance();
    }

    default SessionIdGenerator getSessionIdGenerator() {
        return DefaultSessionIdGenerator.getInstance();
    }

    default MessageIdGenerator getMessageIdGenerator() {
        return DefaultMessageIdGenerator.getInstance();
    }
}
