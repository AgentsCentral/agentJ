package ai.agentscentral.http.config;

import ai.agentscentral.core.agentic.Agentic;
import ai.agentscentral.core.context.InMemoryContextManager;
import ai.agentscentral.core.context.InMemoryContextStateManager;
import ai.agentscentral.core.factory.DefaultAgentJFactory;
import ai.agentscentral.core.session.config.ExecutionLimits;
import ai.agentscentral.core.session.id.DefaultMessageIdGenerator;
import ai.agentscentral.core.session.id.DefaultSessionIdGenerator;
import ai.agentscentral.core.session.id.MessageIdGenerator;
import ai.agentscentral.core.session.id.SessionIdGenerator;
import ai.agentscentral.core.session.processor.DefaultSessionProcessor;
import ai.agentscentral.core.session.processor.SessionProcessor;
import ai.agentscentral.http.request.JsonRequestExtractor;
import ai.agentscentral.http.request.RequestExtractor;
import ai.agentscentral.http.request.SessionIdExtractor;
import ai.agentscentral.http.request.TrailingRequestPathSessionIdExtractor;
import com.fasterxml.jackson.databind.ObjectMapper;

public record AgenticConfig(SessionProcessor processor,
                            RequestExtractor requestExtractor,
                            SessionIdExtractor sessionIdExtractor,
                            SessionIdGenerator sessionIdGenerator,
                            MessageIdGenerator messageIdGenerator) {


    public static AgenticConfigBuilder builder() {
        return new AgenticConfigBuilder();
    }


    public static class AgenticConfigBuilder {

        private SessionProcessor processor;
        private RequestExtractor requestExtractor;
        private SessionIdExtractor sessionIdExtractor;
        private SessionIdGenerator sessionIdGenerator;
        private MessageIdGenerator messageIdGenerator;


        public AgenticConfig build() {
            return new AgenticConfig(processor,
                    requestExtractor,
                    sessionIdExtractor,
                    sessionIdGenerator,
                    messageIdGenerator);
        }

        public AgenticConfigBuilder defaultConfig(String path, Agentic agentic) {
            this.processor = new DefaultSessionProcessor(agentic,
                    DefaultAgentJFactory.getInstance(),
                    new InMemoryContextStateManager(),
                    new InMemoryContextManager(),
                    ExecutionLimits.defaultExecutionLimits());
            this.requestExtractor = new JsonRequestExtractor(new ObjectMapper());
            this.sessionIdExtractor = new TrailingRequestPathSessionIdExtractor(path);
            this.sessionIdGenerator = DefaultSessionIdGenerator.getInstance();
            this.messageIdGenerator = DefaultMessageIdGenerator.getInstance();
            return this;
        }

    }

}
