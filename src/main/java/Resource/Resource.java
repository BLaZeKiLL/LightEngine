package Resource;

import Data.RawModel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class Resource {

    private static GlLoader loader;

    public static void initialize() {
        loader = new GlLoader();
    }

    public static GlLoader getLoader() {
        return loader;
    }

    public static void cleanUp() {
        loader.cleanUp();
    }

    public static int loadShader(String path, int type) throws FileNotFoundException {
        return ShaderLoader.loadShader(load(path), type);
    }

    public static int loadTexture(String path) throws IOException {
        return ImageLoader.loadTexture(load(path), loader);
    }

    public static RawModel loadModel(String path) throws FileNotFoundException {
        return OBJLoader.loadOBJ(load(path), loader);
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
