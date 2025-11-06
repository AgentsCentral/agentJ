package ai.agentscentral.core.factory;

public class DefaultAgentJFactory implements AgentJFactory {

    private static final AgentJFactory instance = new DefaultAgentJFactory();

    private DefaultAgentJFactory() {
    }


    public static AgentJFactory getInstance() {
        return instance;
    }
}
