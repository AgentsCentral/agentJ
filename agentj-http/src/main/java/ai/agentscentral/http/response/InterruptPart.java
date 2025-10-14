package ai.agentscentral.http.response;

import ai.agentscentral.core.session.message.ToolInterruptParameter;

import java.util.List;
import java.util.Map;

public record InterruptPart(String toolCallId,
                            String renderer,
                            Map<String, Object> toolCallParameters,
                            List<ToolInterruptParameter> interruptParameters) {
}
