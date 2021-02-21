package com.example.loadin_app.ui.opengl;

import android.graphics.Point;

import java.util.Arrays;
import java.util.stream.Stream;

public class Hexahedron extends Shape {
    private float mWidth, mHeight, mLength;

    public float getWidth() {
        return mWidth;
    }

    public float getHeight() {
        return mHeight;
    }

    public float getLength() {
        return mLength;
    }

    private Triangle[] mTriangles;

    public Hexahedron(float width, float height, float length){
        mWidth = width;
        mHeight = height;
        mLength = length;

        Vector p1 = new Vector(0, 0, 0);
        Vector p2 = new Vector(width, 0, 0);
        Vector p3 = new Vector(width, length, 0);
        Vector p4 = new Vector(0, length, 0);
        Vector p5 = new Vector(0, 0, height);
        Vector p6 = new Vector(width, 0, height);
        Vector p7 = new Vector(width, length, height);
        Vector p8 = new Vector(0, length, height);


        //6 sides
        //2 triangles per side
        mTriangles = new Triangle[]{
                //base
                new Triangle(p4, p2, p1),
                new Triangle(p4, p3, p2),
                //top
                new Triangle(p5, p6, p7),
                new Triangle(p5, p7, p8),
                //front
                new Triangle(p1, p2, p6),
                new Triangle(p1, p6, p5),
                //back
                new Triangle(p3, p4, p8),
                new Triangle(p3, p8, p7),
                //right
                new Triangle(p2, p3, p7),
                new Triangle(p2, p7, p6),
                //left
                new Triangle(p4, p1, p5),
                new Triangle(p4, p5, p8),

        };


    }

    @Override
    public Stream<Triangle> getTriangles() {
        return Arrays.stream( mTriangles);
    }
}
