package com.example.loadin_app.ui.opengl;

import android.graphics.Point;

import com.example.loadin_app.ui.opengl.programs.IPlaceable;
import com.example.loadin_app.ui.opengl.programs.OpenGLVariableHolder;

import java.util.Arrays;
import java.util.stream.Stream;

public abstract class Hexahedron extends Shape {
    protected float width, height, length;

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getLength() {
        return length;
    }



    public Hexahedron(float width, float height, float length, IPlaceable parent){
        super(parent);
        this.width = width;
        this.height = height;
        this.length = length;



    }



}
