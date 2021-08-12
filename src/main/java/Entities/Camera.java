package Entities;

import Engine.Window;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class Camera {

    private final Vector3f translation = new Vector3f(0, 0, 0);
    private float pitch, yaw, roll;
    private final Window window;

    private final float moveSpeed;

    public Camera(Window window, float moveSpeed) {
        this.window = window;
        this.moveSpeed = moveSpeed;
    }

    public void move() {
        if (GLFW.glfwGetKey(window.getWindowID(), GLFW.GLFW_KEY_W) == 1) {
            translation.z -= moveSpeed;
        }
        if (GLFW.glfwGetKey(window.getWindowID(), GLFW.GLFW_KEY_D) == 1) {
            translation.x += moveSpeed;
        }
        if (GLFW.glfwGetKey(window.getWindowID(), GLFW.GLFW_KEY_A) == 1) {
            translation.x -= moveSpeed;
        }
        if (GLFW.glfwGetKey(window.getWindowID(), GLFW.GLFW_KEY_S) == 1) {
            translation.z += moveSpeed;
        }
        if (GLFW.glfwGetKey(window.getWindowID(), GLFW.GLFW_KEY_LEFT_SHIFT) == 1) {
            translation.y += moveSpeed;
        }
        if (GLFW.glfwGetKey(window.getWindowID(), GLFW.GLFW_KEY_LEFT_CONTROL) == 1) {
            translation.y -= moveSpeed;
        }
    }

    public Vector3f getTranslation() {
        return translation;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }
}
