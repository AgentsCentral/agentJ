package ai.agentscentral.core.factory;

import ai.agentscentral.core.handoff.HandoffsExtractor;
import ai.agentscentral.core.tool.ToolBagToolsExtractor;

public class DefaultAgentJFactory implements AgentJFactory {

    private static final AgentJFactory instance = new DefaultAgentJFactory();

    private DefaultAgentJFactory() {
    }

    public ToolBagToolsExtractor getToolBagToolsExtractor() {
        return ToolBagToolsExtractor.getInstance();
    }

    public HandoffsExtractor getHandoffsExtractor() {
        return HandoffsExtractor.getInstance();
    }


    public static AgentJFactory getInstance() {
        return instance;
    }
}
