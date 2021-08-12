package ToolBox;

import Entities.Camera;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Maths {

    public static Matrix4f createTransformationMatrix(Vector3f translation, Vector3f rotation, float scale) {
        // Blank constructor sets to identity
        Matrix4f matrix = new Matrix4f();
        matrix.translate(translation);
        matrix.rotateXYZ(toRadians(rotation));
        matrix.scale(scale);
        return matrix;
    }

    public static Matrix4f createViewMatrix(Camera camera) {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.rotateXYZ(Math.toRadians(camera.getPitch()), Math.toRadians(camera.getYaw()), Math.toRadians(camera.getRoll()));
        viewMatrix.translate(new Vector3f(-camera.getTranslation().x, -camera.getTranslation().y, -camera.getTranslation().z));
        return viewMatrix;
    }

    public static Vector3f toRadians(Vector3f rotation) {
        return new Vector3f(Math.toRadians(rotation.x), Math.toRadians(rotation.y), Math.toRadians(rotation.z));
    }

}
