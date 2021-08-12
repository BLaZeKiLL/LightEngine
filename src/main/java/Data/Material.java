package Data;

public class Material {

    private final int textureID;

    private float shineDamper;
    private float specularity;

    public Material(int textureID, float shineDamper, float specularity) {
        this.textureID = textureID;
        this.shineDamper = shineDamper;
        this.specularity = specularity;
    }

    public int getTextureID() {
        return textureID;
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    public float getSpecularity() {
        return specularity;
    }

    public void setSpecularity(float specularity) {
        this.specularity = specularity;
    }

}
