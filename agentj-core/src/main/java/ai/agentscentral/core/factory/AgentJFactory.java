package ai.agentscentral.core.factory;

import ai.agentscentral.core.handoff.HandoffsExtractor;
import ai.agentscentral.core.tool.ToolBagToolsExtractor;

/**
 * AgentJFactory
 *
 *
 * @author Rizwan Idrees
 */
public class AgentJFactory {

    private static final AgentJFactory instance = new AgentJFactory();

    private AgentJFactory() {
    }


    public ToolBagToolsExtractor getToolBagToolsExtractor(){
        return ToolBagToolsExtractor.getInstance();
    }

    public HandoffsExtractor getHandoffsExtractor(){
        return HandoffsExtractor.getInstance();
    }


    public static AgentJFactory getInstance() {
        return instance;
    }

}
