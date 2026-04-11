# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

```bash
# Full build
./mvnw clean install

# Build specific module (with dependencies)
./mvnw clean install -am -pl agentj-anthropic

# Skip tests
./mvnw clean package -DskipTests

# Run all tests for a module
./mvnw test -pl agentj-core

# Run a single test class
./mvnw test -Dtest=AnthropicClientTest

# Run a single test class within a specific module
./mvnw test -pl agentj-anthropic -Dtest=AnthropicClientTest
```

Note: Anthropic/OpenAI tests require API key environment variables. MongoDB integration tests use TestContainers (requires Docker).

## Architecture Overview

AgentJ is a Java multi-agent development kit (JDK 21+) for building, orchestrating, and deploying AI agents. It is structured as a multi-module Maven project.

### Core Abstractions (agentj-core)

- **Agent** — the primary unit, composed of a name, model, instructors, tools (`ToolBag`s), and handoffs
- **Team** — orchestrates multiple agents; supports handoff-based or routing-based coordination with a designated leader
- **ToolBag** — a marker interface for classes containing `@Tool`-annotated methods; tool parameters are declared with `@ToolParam`; discovery is reflection-based
- **Handoff** — allows an agent to delegate a conversation to another agent, with routing instructions
- **Session / Context** — conversation messages are persisted via a `ContextManager`; in-memory and MongoDB implementations exist; message types include User, Assistant, Tool, Handoff, Developer, Interrupt
- **ProviderFactory** — SPI interface implemented by each LLM provider module (Anthropic, OpenAI) to produce an `AgentExecutor`

### Execution Flow

1. HTTP request arrives (Jetty via agentj-jetty)
2. Session context is retrieved (in-memory or MongoDB)
3. `AgentExecutor` (Anthropic or OpenAI) sends messages to the LLM
4. Tool calls in the response are extracted, executed, and fed back
5. `HandoffExecutor` or `TeamExecutor` routes if needed
6. Final response is returned; message history is persisted

### Module Responsibilities

| Module | Purpose |
|---|---|
| `agentj-core` | Agent, Team, Tool, Handoff, Session, Context abstractions and executors |
| `agentj-bom` | Central dependency version management (import in POMs) |
| `agentj-anthropic` | Anthropic API client, factory, and message/tool conversion |
| `agentj-openai` | OpenAI API client, factory, and message/tool conversion |
| `agentj-http` | HTTP handler abstraction, CORS/auth filters, health probes |
| `agentj-jetty` | Jetty 12 server runner with virtual thread pool support |
| `agentj-mongodb` | MongoDB-backed `ContextManager` and `ContextStateManager` |
| `agentj-messaging` | Placeholder for future messaging infrastructure |
| `agentj-collab` | Placeholder for future collaboration features |

### Key Packages

- `ai.agentscentral.core.agentic.executor` — `DefaultAgentExecutor`, `DefaultHandoffExecutor`, `TeamExecutor`
- `ai.agentscentral.core.tool` — tool registration, parameter extraction, invocation
- `ai.agentscentral.core.annotation` — `@Tool`, `@ToolParam`, `@Interrupt`, `@InterruptParam`
- `ai.agentscentral.core.context` — `ContextManager` interface and in-memory implementation
- `ai.agentscentral.mongodb.context` — MongoDB implementations of context managers
- `ai.agentscentral.http.config` — `HttpConfig`, `AgentConfig` (used to wire an agent to an HTTP endpoint)
- `ai.agentscentral.jetty.runner` — `JettyHttpRunner` entry point for starting the server

### Provider Integration Pattern

Each provider (Anthropic, OpenAI) follows the same structure:
1. `*Config` — API credentials and connection settings
2. `*Client` — low-level HTTP client wrapping the provider REST API
3. `*Factory` — implements `ProviderFactory`, wires the client into an `AgentExecutor`
4. `*AgentExecutor` / processor — converts AgentJ message types to/from provider-specific formats and manages the tool-call loop

### Publishing

The project publishes to Maven Central via the Sonatype Central Publishing plugin. GPG signing is required for releases. The release plugin manages version bumping.