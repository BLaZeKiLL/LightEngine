package Engine;

import Entities.Camera;
import Entities.Entity;
import Entities.Light;
import Data.Model;
import Shader.LitShader;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterRenderer {

    private final Renderer renderer;
    private final LitShader shader;

    private final Map<Model, List<Entity>> entities = new HashMap<>();

    public MasterRenderer(Window window, Vector4f bgColorRGBA, LitShader shader) {
        this.shader = shader;
        renderer = new Renderer(window, this.shader, bgColorRGBA);
    }

    public void render(Light sun, Camera camera) {
        renderer.prepare();
        shader.start();
        shader.loadLight(sun);
        shader.loadViewMatrix(camera);
        renderer.render(entities);
        shader.stop();
        entities.clear();
    }

    public void processEntity(Entity entity) {
        Model entityModel = entity.getModel();
        List<Entity> batch = entities.get(entityModel);
        if (batch != null) {
            batch.add(entity);
        } else {
            List<Entity> newBatch = new ArrayList<>();
            newBatch.add(entity);
            entities.put(entityModel, newBatch);
        }
    }

    public void cleanUp() {
        shader.cleanUp();
    }

}
