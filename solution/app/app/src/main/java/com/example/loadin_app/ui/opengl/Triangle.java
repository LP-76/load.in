package com.example.loadin_app.ui.opengl;

import java.util.Arrays;
import java.util.stream.Stream;

public class Triangle implements IConvertToFloat {
   private final Vector[] values = new Vector[3];
    public Triangle(Vector p1, Vector p2, Vector p3){
        values[0] = p1;
        values[1] = p2;
        values[2] = p3;
    }



    @Override
    public Stream<Float> asFloats() {
        return Arrays.stream(values).flatMap(i -> i.asFloats());
    }
}
