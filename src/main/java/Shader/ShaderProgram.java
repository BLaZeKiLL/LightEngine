package Shader;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL33;
import Resource.Resource;

import java.io.FileNotFoundException;
import java.nio.FloatBuffer;

public abstract class ShaderProgram {

    private final int programID;
    private final int vertexShaderID;
    private final int fragmentShaderID;

    private static final FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    public ShaderProgram(String vertexFile, String fragmentFile) throws FileNotFoundException {
        vertexShaderID = Resource.loadShader(vertexFile, GL33.GL_VERTEX_SHADER);
        fragmentShaderID = Resource.loadShader(fragmentFile, GL33.GL_FRAGMENT_SHADER);

        programID = GL33.glCreateProgram();
        GL33.glAttachShader(programID, vertexShaderID);
        GL33.glAttachShader(programID, fragmentShaderID);

        bindAttributes();
        GL33.glLinkProgram(programID);
        GL33.glValidateProgram(programID);

        getAllUniformLocations();
    }

    protected abstract void getAllUniformLocations();

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

    protected abstract void bindAttributes();

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