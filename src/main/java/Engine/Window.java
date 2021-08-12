package Engine;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.Callback;
import org.lwjgl.system.Configuration;

import static org.lwjgl.Version.getVersion;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private final long windowID;
    private final int width;
    private final int height;

    public Window(int width, int height, String tittle, boolean FULLSCREEN, boolean DEBUG) {
        // error to be printed to standard print stream
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Create the windowID
        if (FULLSCREEN) {
            windowID = glfwCreateWindow(1920, 1080, tittle, glfwGetPrimaryMonitor(), NULL);
        } else {
            windowID = glfwCreateWindow(width, height, tittle, NULL, NULL);
        }

        if ( windowID == NULL ) {
            throw new RuntimeException("Failed to create the GLFW windowID");
        }

        this.width = width;
        this.height = height;

        glfwMakeContextCurrent(windowID);
        glfwSwapInterval(1);

        // JVM and OpenGL linking
        createCapabilities();
        if (DEBUG) {
            GLUtil.setupDebugMessageCallback();
            Configuration.DEBUG.set(true);
        }

        System.out.println("LWJGL " + getVersion() + " Initialized");
    }

    public long getWindowID() {
        return windowID;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
