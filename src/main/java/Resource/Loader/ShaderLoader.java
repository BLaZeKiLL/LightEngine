package Resource.Loader;

import org.lwjgl.opengl.GL33;

import java.io.*;

public class ShaderLoader {

    public static int loadShader(InputStream stream, int type) {
        StringBuilder shaderSource = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Could not read the file");
            e.printStackTrace();
            System.exit(-1);
        }

        int shaderID = GL33.glCreateShader(type);

        GL33.glShaderSource(shaderID, shaderSource);
        GL33.glCompileShader(shaderID);

        if (GL33.glGetShaderi(shaderID, GL33.GL_COMPILE_STATUS) == GL33.GL_FALSE) {
            System.out.println(GL33.glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader.");
            System.exit(-1);
        }

        return shaderID;
    }

}
