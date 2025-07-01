package ai.agentscentral.core.model;

/**
 * Model
 *
 * @author Rizwan Idrees
 */
public class Model {

    private final String model;
    private final ModelConfig config;

    public Model(String model, ModelConfig config) {
        this.model = model;
        this.config = config;
    }

    public String getModel() {
        return model;
    }

    public ModelConfig getConfig() {
        return config;
    }
}
