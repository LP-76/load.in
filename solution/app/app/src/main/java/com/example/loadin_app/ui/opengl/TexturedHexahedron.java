package com.example.loadin_app.ui.opengl;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.stream.Stream;

public class TexturedHexahedron extends Hexahedron{

    private TexturedTriangle[] mTriangles;
    private Vector[] textureCoordinates;


    public TexturedHexahedron(float width, float height, float length){
        super(width, height, length);


        //IMPORTANT, the Z axis is considered horizontal and Y is considered the up direction

        float x = width;
        float z = length;
        float y = height;
        Color white = new Color(1f,1f,1f,1f);

        Vector p1 = new Vector(0, 0, 0, white);
        Vector p2 = new Vector(x, 0, 0, white);
        Vector p3 = new Vector(x, 0, z, white);
        Vector p4 = new Vector(0, 0, z, white);
        Vector p5 = new Vector(0, y, 0, white);
        Vector p6 = new Vector(x, y, 0f, white);
        Vector p7 = new Vector(x, y, z, white);
        Vector p8 = new Vector(0, y, z, white);

        Vector bottomLeft = new Vector(0f, 0f, 0f);
        Vector bottomRight = new Vector(1f, 0f, 0f);
        Vector topRight = new Vector(1f, 1f, 0f);
        Vector topLeft = new Vector(0f, 1f, 0f);

        //6 sides
        //2 triangles per side
        mTriangles = new TexturedTriangle[]{
                //base
                new TexturedTriangle(p1, p4, p3, topLeft, bottomLeft, bottomRight),
                new TexturedTriangle(p2, p1, p3, topRight, topLeft, bottomRight),
//                //top
                new TexturedTriangle(p5, p7, p6, bottomLeft, topRight, bottomRight),
                new TexturedTriangle(p5, p8, p7, bottomLeft, topLeft, topRight),
//                //front
                new TexturedTriangle(p1, p6, p2, bottomLeft, topRight, bottomRight),
                new TexturedTriangle(p1, p5, p6, bottomLeft, topLeft, topRight),
//                //back
                new TexturedTriangle(p3, p8, p4, bottomLeft, topRight, bottomRight),
                new TexturedTriangle(p3, p7, p8, bottomLeft, topLeft, topRight),
//                //right
                new TexturedTriangle(p2, p7, p3, bottomLeft, topRight, bottomRight),
                new TexturedTriangle(p2, p6, p7, bottomLeft, topLeft, topRight),
//                //left
                new TexturedTriangle(p4, p5, p1, bottomLeft, topRight, bottomRight),
                new TexturedTriangle(p4, p8, p5, bottomLeft, topLeft, topRight),

        };


    }


    @Override
    public void move(Vector direction) {
        for(Triangle t : mTriangles){
            t.move(direction);
        }
    }

    @Override
    public Stream<Triangle> getTriangles() {

        return Arrays.stream( mTriangles);
    }

    public Stream<TexturedTriangle> getTexturedTriangles(){
        return Arrays.stream(mTriangles);
    }
}
