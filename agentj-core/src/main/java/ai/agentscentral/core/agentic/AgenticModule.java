package ai.agentscentral.core.agentic;

import ai.agentscentral.core.context.ContextModule;
import ai.agentscentral.core.handoff.HandoffModule;
import ai.agentscentral.core.session.SessionModule;
import ai.agentscentral.core.tool.ToolModule;
import jakarta.annotation.Nonnull;

public record AgenticModule(@Nonnull ContextModule contextModule,
                            @Nonnull SessionModule sessionModule,
                            @Nonnull ToolModule toolModule,
                            @Nonnull HandoffModule handoffModule) {


    public static AgenticModule defaultAgenticModule(){
        return AgenticModule.builder().build();
    }

    /**
     * Creates a new builder for AgenticModule.
     */
    public static AgenticModuleBuilder builder() {
        return new AgenticModuleBuilder();
    }

    /**
     * Builder class for AgenticModuleBuilder.
     */
    public static class AgenticModuleBuilder {

        private ContextModule contextModule = ContextModule.defaultContextModule();
        private SessionModule sessionModule = SessionModule.defaultSessionModule();
        private ToolModule toolModule = ToolModule.defaultToolModule();
        private HandoffModule handoffModule = HandoffModule.defaultHandoffModule();

        private AgenticModuleBuilder() {
        }

        public AgenticModuleBuilder contextModule(ContextModule contextModule) {
            this.contextModule = contextModule;
            return this;
        }

        public AgenticModuleBuilder sessionModule(SessionModule sessionModule) {
            this.sessionModule = sessionModule;
            return this;
        }

        public AgenticModuleBuilder toolModule(ToolModule toolModule) {
            this.toolModule = toolModule;
            return this;
        }


        public AgenticModuleBuilder handoffModule(HandoffModule handoffModule) {
            this.handoffModule = handoffModule;
            return this;
        }

        public AgenticModule build() {
            return new AgenticModule(
                    contextModule,
                    sessionModule,
                    toolModule,
                    handoffModule
            );
        }
    }
}
