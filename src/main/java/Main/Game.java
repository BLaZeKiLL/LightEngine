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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;

public class Game {

    public static void main(String[] args) {
        // Initialization
        Window window = new Window(1280, 720, "LWJGL 3", false, true);
        Resource.initialize();

        Model model = null;
        LitShader shader = null;

        // Resource Loading
        try {
            model = new Model(
                    Resource.loadModel("Models/Tree01.obj"),
                    new Material(Resource.loadTexture("Textures/gold.png"), 10, 1)
            );

            shader = new LitShader("Shaders/lit.glsl");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        // Entity Creation
        List<Entity> dragons = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 25; i++) {
            float px = random.nextFloat() * 100 - 50;
            float py = random.nextFloat() * 100 - 50;
            float pz = random.nextFloat() * -300;

            float rx = random.nextFloat() * 180f;
            float ry = random.nextFloat() * 180f;

            Entity dragon = new Entity(model, new Vector3f(px, py, pz), new Vector3f(rx, ry, 0), 1);

            dragons.add(dragon);
        }

        Light light = new Light(new Vector3f(0, 0, -20), new Vector3f(1, 1, 1));
        Camera camera = new Camera(window, 0.1f);

        MasterRenderer renderer = new MasterRenderer(window, new Vector4f(1.0f, 1.0f, 1.0f, 1.0f), shader);

        // Main Loop
        while (!glfwWindowShouldClose(window.getWindowID())) {
            camera.move();
            for (Entity dragon : dragons) {
                renderer.processEntity(dragon);
            }
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