package com.example.loadin_app.ui.opengl;

import java.util.Arrays;
import java.util.stream.Stream;

public class TexturedTriangle extends Triangle {
    private final Vector[] textureValues = new Vector[3];
    public TexturedTriangle(Vector p1, Vector p2, Vector p3, Vector tex1, Vector tex2, Vector tex3){
        super(p1, p2, p3);
        textureValues[0] = tex1;
        textureValues[1] = tex2;
        textureValues[2] = tex3;
    }

    public Stream<Float> getTextureCoordinates(){
        return Arrays.stream(textureValues).flatMap(i -> Arrays.stream(new Float[]{i.getX(), i.getY() }));  //only map x and y
    }

}
