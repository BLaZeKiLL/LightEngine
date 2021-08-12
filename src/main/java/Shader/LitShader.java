package Shader;

import Entities.Camera;
import Entities.Light;
import Utils.Maths;
import org.joml.Matrix4f;

import java.io.FileNotFoundException;

public class LitShader extends ShaderProgram {

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightTranslation;
    private int location_lightColour;
    private int location_shineDamper;
    private int location_specularity;

    public LitShader(String path) throws FileNotFoundException {
        super(path);
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_lightTranslation = super.getUniformLocation("lightTranslation");
        location_lightColour = super.getUniformLocation("lightColour");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_specularity = super.getUniformLocation("specularity");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "normal");
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        super.loadMatrix(location_projectionMatrix, matrix);
    }

    public void loadViewMatrix(Camera camera) {
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }

    public void loadLight(Light light) {
        super.loadVector(location_lightTranslation, light.getTranslation());
        super.loadVector(location_lightColour, light.getColour());
    }

    public void loadSpecular(float damper, float specularity) {
        super.loadFloat(location_shineDamper, damper);
        super.loadFloat(location_specularity, specularity);
    }
}
