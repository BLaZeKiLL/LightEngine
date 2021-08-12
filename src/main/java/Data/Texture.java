package Data;

import org.lwjgl.opengl.GL33;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Texture {

    private final int id;
    private final int width;
    private final int height;

    public Texture(int width, int height) {
        id = GL33.glGenTextures();
        this.width = width;
        this.height = height;
    }

    public void bind() {
        GL33.glBindTexture(GL33.GL_TEXTURE_2D, id);
    }

    public void setParameter(int name, int value) {
        GL33.glTexParameteri(GL33.GL_TEXTURE_2D, name, value);
    }

    public void uploadData(int width, int height, ByteBuffer data) {
        uploadData(GL33.GL_RGBA8, width, height, GL33.GL_RGBA, data);
    }

    public void uploadData(int internalFormat, int width, int height, int format, ByteBuffer data) {
        GL33.glTexImage2D(GL33.GL_TEXTURE_2D, 0, internalFormat, width, height, 0, format, GL33.GL_UNSIGNED_BYTE, data);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getId() {
        return id;
    }

}