package Engine;

import Data.Model;
import Data.RawModel;
import Entities.Entity;
import Shader.LitShader;
import Utils.Maths;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL33;

import java.util.List;
import java.util.Map;

public class Renderer {

    private static final float FOV = 70.0f;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000.0f;

    private final Vector4f bgColorRGBA;
    private final Window window;
    private final LitShader shader;

    private Matrix4f projectionMatrix;

    public Renderer(Window window, LitShader shader, Vector4f bgColorRGBA) {
        this.window = window;
        this.bgColorRGBA = bgColorRGBA;
        this.shader = shader;

        GL33.glEnable(GL33.GL_CULL_FACE);
        GL33.glCullFace(GL33.GL_BACK);

        createProjectionMatrix();

        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void prepare() {
        GL33.glEnable(GL33.GL_DEPTH_TEST);
        GL33.glClear(GL33.GL_COLOR_BUFFER_BIT|GL33.GL_DEPTH_BUFFER_BIT);
        GL33.glClearColor(bgColorRGBA.x, bgColorRGBA.y, bgColorRGBA.z, bgColorRGBA.w);
    }

    public void render(Map<Model, List<Entity>> entities) {
        for (Model model : entities.keySet()) {
            prepareTexturedModel(model);
            List<Entity> batch = entities.get(model);
            for (Entity entity : batch) {
                prepareInstance(entity);
                GL33.glDrawElements(GL33.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL33.GL_UNSIGNED_INT, 0);
            }
            unbindTexturedModel();
        }
    }

    private void prepareTexturedModel(Model model) {
        RawModel rawModel = model.getRawModel();

        GL33.glBindVertexArray(rawModel.getVaoID());
        GL33.glEnableVertexAttribArray(0);
        GL33.glEnableVertexAttribArray(1);
        GL33.glEnableVertexAttribArray(2);

        shader.loadSpecular(model.getMaterial().getShineDamper(), model.getMaterial().getSpecularity());

        GL33.glActiveTexture(GL33.GL_TEXTURE0);
        GL33.glBindTexture(GL33.GL_TEXTURE_2D, model.getMaterial().getTextureID());
    }

    private void unbindTexturedModel() {
        GL33.glDisableVertexAttribArray(0);
        GL33.glDisableVertexAttribArray(1);
        GL33.glDisableVertexAttribArray(2);
        GL33.glBindVertexArray(0);
    }

    private void prepareInstance(Entity entity) {
        shader.loadTransformationMatrix(Maths.createTransformationMatrix(entity.getPosition(), entity.getRotation(), entity.getScale()));
    }

    private void createProjectionMatrix() {
        float aspectRatio = (float) window.getWidth() / (float) window.getHeight();
        float y_scale = (float) (1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio;
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00(x_scale);
        projectionMatrix.m11(y_scale);
        projectionMatrix.m22(-((FAR_PLANE + NEAR_PLANE) / frustum_length));
        projectionMatrix.m23(-1);
        projectionMatrix.m32(-((2 * NEAR_PLANE * FAR_PLANE) / frustum_length));
        projectionMatrix.m33(0);
    }

}
