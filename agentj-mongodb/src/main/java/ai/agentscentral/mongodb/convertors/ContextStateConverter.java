package ai.agentscentral.mongodb.convertors;

import ai.agentscentral.core.context.ContextState;
import ai.agentscentral.core.context.DefaultContextState;
import ai.agentscentral.core.convertors.Convertor;
import ai.agentscentral.mongodb.enums.ContextStateType;
import ai.agentscentral.mongodb.model.ContextStateDocument;

/**
 * ContextStateConverter
 *
 * @author Mustafa Bhuiyan
 */
public class ContextStateConverter {

    // Converter to convert ContextState -> ContextStateDocument
    public static Convertor<ContextState, ContextStateDocument> contextStateToDocConverter
            = (contextState) -> {
        ContextStateDocument contextStateDocument = new ContextStateDocument();
        contextStateDocument.setContextId(contextState.contextId());
        contextStateDocument.setCurrentTeam(contextState.currentTeam());
        contextStateDocument.setCurrentAgent(contextState.currentAgent());
        contextStateDocument.setPartOfTeam(contextState.partOfTeam());

        switch (contextState) {
            case DefaultContextState defaultCS -> contextStateDocument.setType(ContextStateType.DEFAULT);
            default -> throw new IllegalArgumentException("Unsupported context state type: " + contextState.getClass().getName());
        }
        return contextStateDocument;
    };

    // Converter to convert ContextStateDocument -> ContextState
    public static Convertor<ContextStateDocument, ContextState> docToContextStateConverter
            = (contextStateDocument) -> {
        return switch (contextStateDocument.getType()) {
            case DEFAULT -> new DefaultContextState(contextStateDocument.getContextId(),
                    contextStateDocument.getCurrentTeam(), contextStateDocument.getCurrentAgent(),
                    contextStateDocument.getPartOfTeam());
            default -> throw new IllegalArgumentException("Unsupported context state document type: " + contextStateDocument.getType());
        };
    };
}
