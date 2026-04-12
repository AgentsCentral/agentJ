package ai.agentscentral.core.agentic;

import ai.agentscentral.core.context.ContextModule;
import ai.agentscentral.core.handoff.HandoffModule;
import ai.agentscentral.core.session.SessionModule;
import ai.agentscentral.core.tool.ToolModule;
import jakarta.annotation.Nonnull;

/**
 * Aggregates the shared infrastructure modules required by the agentic execution framework.
 *
 * <p>An {@code AgenticModule} is constructed once and passed to
 * {@link ai.agentscentral.core.agentic.executor.AgenticExecutorInitializer} and
 * {@link ai.agentscentral.core.agentic.executor.DefaultAgentExecutor}, which pull the
 * individual sub-modules they need. All four sub-modules have sensible in-memory defaults
 * that can be obtained via {@link #defaultAgenticModule()}; use {@link #builder()} to
 * substitute custom implementations (e.g. a MongoDB-backed context manager).</p>
 *
 * @param contextModule  provides the {@link ai.agentscentral.core.context.ContextManager}
 *                       (message persistence) and
 *                       {@link ai.agentscentral.core.context.ContextStateManager}
 *                       (active-agent tracking)
 * @param sessionModule  provides the {@link ai.agentscentral.core.session.id.SessionIdGenerator}
 *                       and {@link ai.agentscentral.core.session.id.MessageIdGenerator}
 * @param toolModule     provides the {@link ai.agentscentral.core.tool.ToolsExtractor}
 *                       (reflection-based tool discovery) and the
 *                       {@link ai.agentscentral.core.tool.ToolCallExecutor} (tool invocation)
 * @param handoffModule  provides the {@link ai.agentscentral.core.handoff.HandoffsExtractor}
 *                       used to index an agent's declared handoff targets
 *
 * @author Rizwan Idrees
 */
public record AgenticModule(@Nonnull ContextModule contextModule,
                            @Nonnull SessionModule sessionModule,
                            @Nonnull ToolModule toolModule,
                            @Nonnull HandoffModule handoffModule) {

    /**
     * Creates an {@code AgenticModule} with all default in-memory implementations.
     *
     * <p>Equivalent to {@code AgenticModule.builder().build()}.</p>
     *
     * @return a fully configured module using in-memory context, session ID generators,
     *         reflection-based tool extraction, and the default handoff extractor
     */
    public static AgenticModule defaultAgenticModule() {
        return AgenticModule.builder().build();
    }

    /**
     * Returns a new builder pre-populated with all default implementations.
     *
     * <p>Call individual setter methods to replace only the sub-modules that need
     * customisation, then call {@link AgenticModuleBuilder#build()}.</p>
     *
     * @return a new {@link AgenticModuleBuilder}
     */
    public static AgenticModuleBuilder builder() {
        return new AgenticModuleBuilder();
    }

    /**
     * Builder for {@link AgenticModule}.
     *
     * <p>Each field is initialised to its default implementation so that callers only need
     * to override the sub-modules they wish to customise.</p>
     */
    public static class AgenticModuleBuilder {

        private ContextModule contextModule = ContextModule.defaultContextModule();
        private SessionModule sessionModule = SessionModule.defaultSessionModule();
        private ToolModule toolModule = ToolModule.defaultToolModule();
        private HandoffModule handoffModule = HandoffModule.defaultHandoffModule();

        private AgenticModuleBuilder() {
        }

        /**
         * Overrides the context module (message persistence and active-agent state).
         *
         * @param contextModule the context module to use
         * @return this builder
         */
        public AgenticModuleBuilder contextModule(ContextModule contextModule) {
            this.contextModule = contextModule;
            return this;
        }

        /**
         * Overrides the session module (session and message ID generators).
         *
         * @param sessionModule the session module to use
         * @return this builder
         */
        public AgenticModuleBuilder sessionModule(SessionModule sessionModule) {
            this.sessionModule = sessionModule;
            return this;
        }

        /**
         * Overrides the tool module (tool extractor and tool call executor).
         *
         * @param toolModule the tool module to use
         * @return this builder
         */
        public AgenticModuleBuilder toolModule(ToolModule toolModule) {
            this.toolModule = toolModule;
            return this;
        }

        /**
         * Overrides the handoff module (handoff extractor).
         *
         * @param handoffModule the handoff module to use
         * @return this builder
         */
        public AgenticModuleBuilder handoffModule(HandoffModule handoffModule) {
            this.handoffModule = handoffModule;
            return this;
        }

        /**
         * Builds and returns the configured {@link AgenticModule}.
         *
         * @return a new {@code AgenticModule} with the current builder state
         */
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
