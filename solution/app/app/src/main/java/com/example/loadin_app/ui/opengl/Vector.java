package com.example.loadin_app.ui.opengl;

import java.util.Arrays;
import java.util.stream.Stream;

public class Vector implements IConvertToFloat {
    private  Float[] values;

    public Vector(float x, float y, float z){
       values = new Float[3];
        values[0] = x;
       values[1] = y;
       values[2] = z;
    }

    public float getX(){
        return values[0];
    }
    public float getY(){
        return values[1];
    }
    public float getZ(){
        return values[2];
    }

    @Override
    public Stream<Float> asFloats() {
        return Arrays.stream(values);
    }
}
