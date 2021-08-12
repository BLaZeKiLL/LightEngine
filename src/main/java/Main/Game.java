package Main;

import Data.Material;
import Data.Model;
import Engine.MasterRenderer;
import Engine.Window;
import Entities.Camera;
import Entities.Entity;
import Entities.Light;
import Resource.Resource;
import Shader.LitShader;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.io.IOException;

import static org.lwjgl.glfw.GLFW.*;

public class Game {

    public static void main(String[] args) {
        // Initialization
        Window window = new Window(1920, 1080, "LWJGL 3", true, true);
        Resource.initialize();

        Model model = null;
        LitShader shader = null;

        // Resource Loading
        try {
            model = new Model(
                    Resource.loadModel("Models/gun.obj"),
                    new Material(Resource.loadTexture("Textures/gold.png"), 10, 1)
            );

            shader = new LitShader("Shaders/lit.glsl");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        // Entity Creation
        Entity dragon = new Entity(model, new Vector3f(0, -2.5f, -20), new Vector3f(0, 0, 0), 5);
        Light light = new Light(new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
        Camera camera = new Camera(window, 0.1f);

        MasterRenderer renderer = new MasterRenderer(window, shader, new Vector4f(0.4f, 0.4f, 0.4f, 1.0f));

        // Main Loop
        while (!glfwWindowShouldClose(window.getWindowID())) {
            camera.move();

            dragon.rotate(0, 0.5f, 0);
            renderer.processEntity(dragon);
            renderer.render(light, camera);

            glfwSwapBuffers(window.getWindowID());
            glfwPollEvents();
        }

        // Clean up
        renderer.cleanUp();
        Resource.cleanUp();
        glfwTerminate();
    }

}