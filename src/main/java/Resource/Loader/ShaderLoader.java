package Resource.Loader;

import org.lwjgl.opengl.GL33;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ShaderLoader {

    private static final String VERTEX_SOURCE = "#VERTEX";
    private static final String FRAGMENT_SOURCE = "#FRAGMENT";

    public static Map<Integer, Integer> loadShader(InputStream stream) {
        Map<Integer, StringBuilder> shaderSource = new HashMap<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line;

            int type = -1;

            while ((line = reader.readLine()) != null) {
                switch (line) {
                    case VERTEX_SOURCE:
                        type = GL33.GL_VERTEX_SHADER;
                        shaderSource.put(type, new StringBuilder());
                        break;
                    case FRAGMENT_SOURCE:
                        type = GL33.GL_FRAGMENT_SHADER;
                        shaderSource.put(type, new StringBuilder());
                        break;
                    default:
                        shaderSource.get(type).append(line).append("\n");
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Could not read the file");
            e.printStackTrace();
            System.exit(-1);
        }

        Map<Integer, Integer> shaderProgram = new HashMap<>();

        shaderSource.forEach((type, source) -> {
            int ID = GL33.glCreateShader(type);

            GL33.glShaderSource(ID, source);
            GL33.glCompileShader(ID);

            if (GL33.glGetShaderi(ID, GL33.GL_COMPILE_STATUS) == GL33.GL_FALSE) {
                System.out.println(GL33.glGetShaderInfoLog(ID, 500));
                System.err.println("Could not compile shader.");
                System.exit(-1);
            }

            shaderProgram.put(type, ID);
        });

        return shaderProgram;
    }

}
