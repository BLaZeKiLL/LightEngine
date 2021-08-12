package Shader;

import Resource.Resource;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL33;

import java.io.FileNotFoundException;
import java.nio.FloatBuffer;

public abstract class ShaderProgram {

    private final int programID;
    private int vertexShaderID;
    private int fragmentShaderID;

    private static final FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    public ShaderProgram(String path) throws FileNotFoundException {
        var shaderProgram = Resource.loadShader(path);

        shaderProgram.forEach((type, ID) ->  {
            switch (type) {
                case GL33.GL_VERTEX_SHADER:
                    vertexShaderID = ID;
                    break;
                case GL33.GL_FRAGMENT_SHADER:
                    fragmentShaderID = ID;
                    break;
            }
        });

        programID = GL33.glCreateProgram();
        GL33.glAttachShader(programID, vertexShaderID);
        GL33.glAttachShader(programID, fragmentShaderID);

        GL33.glLinkProgram(programID);
        GL33.glValidateProgram(programID);

        bindAttributes();
        getAllUniformLocations();
    }

    protected abstract void getAllUniformLocations();

    protected abstract void bindAttributes();

    protected int getUniformLocation(String uniformName) {
        return GL33.glGetUniformLocation(programID, uniformName);
    }

    protected void loadFloat(int location, float value) {
        GL33.glUniform1f(location, value);
    }

    protected void loadVector(int location, Vector3f vector) {
        GL33.glUniform3f(location, vector.x, vector.y, vector.z);
    }

    protected void loadBoolean(int location, boolean value) {
        GL33.glUniform1f(location, (value ? 1 : 0));
    }

    protected void loadMatrix(int location, Matrix4f matrix) {
        // JOML is nice so no need to flip :)
        GL33.glUniformMatrix4fv(location, false, matrix.get(matrixBuffer));
    }

    protected void bindAttribute(int attribute, String variableName) {
        GL33.glBindAttribLocation(programID, attribute, variableName);
    }

    public void start() {
        GL33.glUseProgram(programID);
    }

    public void stop() {
        GL33.glUseProgram(0);
    }

    public void cleanUp() {
        stop();
        GL33.glDetachShader(programID, vertexShaderID);
        GL33.glDetachShader(programID, fragmentShaderID);
        GL33.glDeleteShader(vertexShaderID);
        GL33.glDeleteShader(fragmentShaderID);
        GL33.glDeleteProgram(programID);
    }

}
