package Resource;

import Data.RawModel;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL33;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class GlLoader {

    private final List<Integer> vaos = new ArrayList<>();
    private final List<Integer> vbos = new ArrayList<>();
    private final List<Integer> ibos = new ArrayList<>();
    private final List<Integer> textures = new ArrayList<>();

    public RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices) {
        int vaoID = createVAO();
        bindIndexBuffer(indices);
        storeDataInAttributeList(0, 3, positions);
        storeDataInAttributeList(1, 2, textureCoords);
        storeDataInAttributeList(2, 3, normals);
        unbindVAO();
        return new RawModel(vaoID, indices.length);
    }

    public int loadTexture(int textureID) {
        textures.add(textureID);
        return textureID;
    }
    
    public void cleanUp() {
        for (int vao : vaos) {
            GL33.glDeleteVertexArrays(vao);
        }
        for (int vbo : vbos) {
            GL33.glDeleteBuffers(vbo);
        }
        for (int ibo : ibos) {
            GL33.glDeleteBuffers(ibo);
        }
        for (int texture : textures) {
            GL33.glDeleteTextures(texture);
        }
    }

    private int createVAO() {
        int vaoID = GL33.glGenVertexArrays();
        vaos.add(vaoID);
        GL33.glBindVertexArray(vaoID);
        return vaoID;
    }

    private void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) {
        int vboID = GL33.glGenBuffers();
        vbos.add(vboID);
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, buffer, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(attributeNumber, coordinateSize, GL33.GL_FLOAT, false, 0, 0);
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, 0);
    }

    private void unbindVAO() {
        GL33.glBindVertexArray(0);
    }

    private void bindIndexBuffer(int[] indices) {
        int iboID = GL33.glGenBuffers();
        ibos.add(iboID);
        GL33.glBindBuffer(GL33.GL_ELEMENT_ARRAY_BUFFER, iboID);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        GL33.glBufferData(GL33.GL_ELEMENT_ARRAY_BUFFER, buffer, GL33.GL_STATIC_DRAW);
    }

    private IntBuffer storeDataInIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

}