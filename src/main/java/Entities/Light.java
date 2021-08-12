package Entities;

import org.joml.Vector3f;

public class Light {

    private Vector3f translation;
    private Vector3f colour;

    public Light(Vector3f translation, Vector3f colour) {
        this.translation = translation;
        this.colour = colour;
    }

    public Vector3f getTranslation() {
        return translation;
    }

    public void setTranslation(Vector3f translation) {
        this.translation = translation;
    }

    public Vector3f getColour() {
        return colour;
    }

    public void setColour(Vector3f colour) {
        this.colour = colour;
    }

}
