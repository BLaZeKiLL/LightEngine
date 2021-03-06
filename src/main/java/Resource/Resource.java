package Resource;

import Data.RawModel;
import Resource.Loader.ImageLoader;
import Resource.Loader.OBJLoader;
import Resource.Loader.ShaderLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

public class Resource {

    private static GLContext context;

    public static void initialize() {
        context = new GLContext();
    }

    public static GLContext getContext() {
        return context;
    }

    public static void cleanUp() {
        context.cleanUp();
    }

    public static Map<Integer, Integer> loadShader(String path) throws FileNotFoundException {
        return ShaderLoader.loadShader(load(path));
    }

    public static int loadTexture(String path) throws IOException {
        return ImageLoader.loadTexture(load(path), context);
    }

    public static RawModel loadModel(String path) throws FileNotFoundException {
        return OBJLoader.loadOBJ(load(path), context);
    }

    public static String path(String path) {
        return Objects.requireNonNull(Resource.class.getClassLoader().getResource(path)).getFile().substring(1);
    }

    private static InputStream load(String path) throws FileNotFoundException {
        InputStream stream = Resource.class.getClassLoader().getResourceAsStream(path);

        if (stream == null) {
            throw new FileNotFoundException("Resource not found at : " + path);
        }

        return stream;
    }

}
