package ai.agentscentral.mongodb.config;

import ai.agentscentral.core.handoff.HandoffInstruction;
import ai.agentscentral.core.session.message.MessagePart;
import ai.agentscentral.core.session.message.TextPart;
import ai.agentscentral.core.tool.ToolCallInstruction;
import com.mongodb.MongoClientSettings;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 * MongoDBCodecRegistry
 *
 * @author Mustafa Bhuiyan
 */
public class MongoDBCodecRegistry {
    public static final CodecRegistry DOCUMENT_CODEC_REGISTRY = createDocumentCodecRegistry();
    public static final CodecRegistry AUTO_CODEC_REGISTRY = createAutoCodecRegistry();

    private static CodecRegistry createAutoCodecRegistry() {
        PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder()
                .automatic(true)
                .build();

        return CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(pojoCodecProvider)
        );
    }

    private static CodecRegistry createDocumentCodecRegistry() {
        PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder()
                .register(
                        MessagePart.class,
                        TextPart.class,
                        ToolCallInstruction.class,
                        HandoffInstruction.class)
                .automatic(true)
                .build();
        return CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(pojoCodecProvider)
        );
    }
}
