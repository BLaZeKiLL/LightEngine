package Resource;

import Data.Texture;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL33;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class ImageLoader {

    public static int loadTexture(InputStream stream, GlLoader loader) throws IOException {
        ByteBuffer image;
        int width, height;

        try (MemoryStack stack = MemoryStack.stackPush()) {
            // Prepare image buffers
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            // Load image
            byte[] data = stream.readAllBytes();
            ByteBuffer buffer = BufferUtils.createByteBuffer(data.length);
            buffer.put(data);
            buffer.flip();

            // Create Image Buffer
            image = STBImage.stbi_load_from_memory(buffer, w, h, comp, 4);

            if (image == null) {
                throw new IOException("Failed to load image");
            }

            width = w.get();
            height = h.get();
        }

        Texture texture = createTexture(width, height, image);

        return loader.loadTexture(texture.getId());
    }

    private static Texture createTexture(int width, int height, ByteBuffer data) {
        Texture texture = new Texture(width, height);
        texture.bind();

        // if Texture go weired look at the params
        texture.setParameter(GL33.GL_TEXTURE_WRAP_S, GL33.GL_REPEAT);
        texture.setParameter(GL33.GL_TEXTURE_WRAP_T, GL33.GL_REPEAT);
        texture.setParameter(GL33.GL_TEXTURE_MIN_FILTER, GL33.GL_LINEAR);
        texture.setParameter(GL33.GL_TEXTURE_MAG_FILTER, GL33.GL_LINEAR);

        texture.uploadData(width, height, data);

        return texture;
    }

}
