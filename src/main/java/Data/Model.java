package Data;

public class Model {

    private final RawModel rawModel;
    private final Material material;

    public Model(RawModel rawModel, Material material) {
        this.rawModel = rawModel;
        this.material = material;
    }

    public RawModel getRawModel() {
        return rawModel;
    }

    public Material getMaterial() {
        return material;
    }

}
