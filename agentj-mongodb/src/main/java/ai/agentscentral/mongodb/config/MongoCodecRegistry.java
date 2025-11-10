package ai.agentscentral.mongodb.config;

import ai.agentscentral.core.handoff.HandoffInstruction;
import ai.agentscentral.core.session.message.AssistantMessage;
import ai.agentscentral.core.session.message.DeveloperMessage;
import ai.agentscentral.core.session.message.HandOffMessage;
import ai.agentscentral.core.session.message.Message;
import ai.agentscentral.core.session.message.MessagePart;
import ai.agentscentral.core.session.message.MessageType;
import ai.agentscentral.core.session.message.TextPart;
import ai.agentscentral.core.session.message.ToolMessage;
import ai.agentscentral.core.session.message.UserMessage;
import ai.agentscentral.core.tool.ToolCallInstruction;
import ai.agentscentral.mongodb.model.MessageDocument;
import com.mongodb.MongoClientSettings;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

public class MongoCodecRegistry {
    public static final CodecRegistry POJO_CODEC_REGISTRY = createPojoCodecRegistry();
    public static final CodecRegistry DOCUMENT_CODEC_REGISTRY = createDocumentCodecRegistry();
    public static final CodecRegistry AUTO_CODEC_REGISTRY = createAutoCodecRegistry();

    private static CodecRegistry createAutoCodecRegistry() {
        PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder()
                .automatic(true)
                .build();

        return CodecRegistries.fromRegistries(
                CodecRegistries.fromProviders(pojoCodecProvider),
                MongoClientSettings.getDefaultCodecRegistry()
        );
    }

    private static CodecRegistry createDocumentCodecRegistry() {
        PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder()
                .register(MessageDocument.class,
                        MessagePart.class,
                        TextPart.class,
                        MessageType.class,
                        ToolCallInstruction.class,
                        HandoffInstruction.class)
                .automatic(true)
                .build();
        return CodecRegistries.fromRegistries(
                CodecRegistries.fromProviders(pojoCodecProvider),
                MongoClientSettings.getDefaultCodecRegistry()
        );
    }
    
    private static CodecRegistry createPojoCodecRegistry() {
        // Build the codec provider
        PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder()
                .register(Message.class,
                        AssistantMessage.class,
                        UserMessage.class,
                        DeveloperMessage.class,
                        ToolMessage.class,
                        HandOffMessage.class,
                        MessagePart.class,
                        TextPart.class)
                .automatic(true)
                .build();
        // Create the final registry, including the RecordCodecProvider
        return CodecRegistries.fromRegistries(
                CodecRegistries.fromProviders(pojoCodecProvider),
                MongoClientSettings.getDefaultCodecRegistry()
        );
    }
}
