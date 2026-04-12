package ai.agentscentral.http.config;

import ai.agentscentral.core.agentic.Agentic;
import ai.agentscentral.core.agentic.AgenticModule;
import ai.agentscentral.core.session.config.ExecutionLimits;
import ai.agentscentral.core.session.id.MessageIdGenerator;
import ai.agentscentral.core.session.id.SessionIdGenerator;
import ai.agentscentral.core.session.processor.DefaultSessionProcessor;
import ai.agentscentral.core.session.processor.SessionProcessor;
import ai.agentscentral.http.request.*;
import tools.jackson.databind.ObjectMapper;

/**
 * Configuration record for an individual agentic HTTP endpoint.
 *
 * <p>Groups all the dependencies needed by {@link ai.agentscentral.http.handler.AgenticHttpHandler}
 * to process a single conversational request: session/message ID generation,
 * session-ID extraction from the HTTP request, request body deserialisation, and the
 * {@link SessionProcessor} that drives the agent execution loop.</p>
 *
 * <p>The convenience method {@link AgenticConfigBuilder#defaultConfig} wires standard
 * implementations (JSON extractor, header-based session ID, default execution limits)
 * and is the recommended starting point.</p>
 *
 * @param processor           the session processor that routes messages to the correct
 *                            agent or team executor
 * @param requestExtractor    deserialises the HTTP request body into a
 *                            {@link ai.agentscentral.http.request.MessageRequest}
 * @param sessionIdExtractor  extracts an existing session identifier from the request
 *                            (e.g. from a header), returning {@link java.util.Optional#empty()}
 *                            if none is present
 * @param sessionIdGenerator  generates a new session identifier when
 *                            {@code sessionIdExtractor} finds none
 * @param messageIdGenerator  generates unique identifiers for each user message
 *
 * @author Rizwan Idrees
 */
public record AgenticConfig(SessionProcessor processor,
                            RequestExtractor requestExtractor,
                            SessionIdExtractor sessionIdExtractor,
                            SessionIdGenerator sessionIdGenerator,
                            MessageIdGenerator messageIdGenerator) {


    /**
     * Returns a new {@link AgenticConfigBuilder}.
     *
     * @return a fresh builder
     */
    public static AgenticConfigBuilder builder() {
        return new AgenticConfigBuilder();
    }

    /**
     * Fluent builder for {@link AgenticConfig}.
     *
     * <p>Use {@link #defaultConfig} to populate all fields with standard implementations
     * before calling {@link #build()}.</p>
     */
    public static class AgenticConfigBuilder {

        /**
         * Creates a builder with all fields initially unset.
         */
        public AgenticConfigBuilder() {
        }

        private SessionProcessor processor;
        private RequestExtractor requestExtractor;
        private SessionIdExtractor sessionIdExtractor;
        private SessionIdGenerator sessionIdGenerator;
        private MessageIdGenerator messageIdGenerator;


        /**
         * Builds the {@link AgenticConfig} from the currently set fields.
         *
         * @return the assembled configuration
         */
        public AgenticConfig build() {
            return new AgenticConfig(processor,
                    requestExtractor,
                    sessionIdExtractor,
                    sessionIdGenerator,
                    messageIdGenerator);
        }

        /**
         * Populates all builder fields with standard implementations suitable for
         * production use: a {@link ai.agentscentral.core.session.processor.DefaultSessionProcessor},
         * a {@link ai.agentscentral.http.request.JsonRequestExtractor},
         * a {@link ai.agentscentral.http.request.SessionIdHeaderExtractor} (reads
         * {@code X-Session-Id}), and the ID generators from the provided module.
         *
         * @param agentic       the agent or team to execute on each request
         * @param agenticModule the module providing execution infrastructure
         * @return this builder
         */
        public AgenticConfigBuilder defaultConfig(Agentic agentic,
                                                  AgenticModule agenticModule) {

            final ExecutionLimits executionLimits = ExecutionLimits.defaultExecutionLimits();
            this.processor = new DefaultSessionProcessor(agentic, agenticModule, executionLimits);

            this.requestExtractor = new JsonRequestExtractor(new ObjectMapper());
            this.sessionIdExtractor = new SessionIdHeaderExtractor();
            this.sessionIdGenerator = agenticModule.sessionModule().sessionIdGenerator();
            this.messageIdGenerator = agenticModule.sessionModule().messageIdGenerator();
            return this;
        }

    }

}
