package ai.agentscentral.mongodb.config;

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
    public static final CodecRegistry AUTO_CODEC_REGISTRY = createAutoCodecRegistry();

    /**
     * Creates a basic auto codec registry with default settings.
     * @return CodecRegistry with basic configuration
     */
    public static CodecRegistry createAutoCodecRegistry() {
        PojoCodecProvider pojoCodecProvider = PojoCodecProvider.builder()
                .automatic(true)
                .build();

        return CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(pojoCodecProvider)
        );
    }
}
