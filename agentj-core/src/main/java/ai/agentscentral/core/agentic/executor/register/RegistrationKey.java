package ai.agentscentral.core.agentic.executor.register;

/**
 * Composite lookup key used by {@link DefaultAgenticRegistrar} to store and retrieve
 * registered agentic entities.
 *
 * <p>An entity is uniquely identified by both its own name and the name of the team it
 * belongs to. Top-level entities use an empty string for {@code partOfTeamName}.</p>
 *
 * @param name           name of the agent or team
 * @param partOfTeamName name of the parent team, or an empty string for top-level entities
 *
 * @author Rizwan Idrees
 */
record RegistrationKey(String name, String partOfTeamName) {
}
