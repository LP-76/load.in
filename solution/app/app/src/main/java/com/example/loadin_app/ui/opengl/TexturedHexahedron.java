package com.example.loadin_app.ui.opengl;

import android.opengl.GLES20;

import com.example.loadin_app.ui.opengl.programs.IPlaceable;
import com.example.loadin_app.ui.opengl.programs.OpenGLProgram;
import com.example.loadin_app.ui.opengl.programs.OpenGLVariableHolder;

import java.util.Arrays;
import java.util.stream.Stream;

public class TexturedHexahedron extends Hexahedron {


    private Face[] faces;

    private Face front;
    private Face back;
    private Face left;
    private Face right;
    private Face bottom;
    private Face top;

    private Vector bottomLeft;  //these represent the 2d texture map coordinates
    private Vector bottomRight;
    private Vector topRight;
    private Vector topLeft;

    public TexturedHexahedron(float width, float height, float length, IPlaceable parent){
        super(width, height, length, parent);


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

         bottomLeft = new Vector(0f, 0f, 0f);
         bottomRight = new Vector(1f, 0f, 0f);
         topRight = new Vector(1f, 1f, 0f);
         topLeft = new Vector(0f, 1f, 0f);

        front = new Face(
                new TexturedTriangle[]{
                        //front
                        new TexturedTriangle(p1, p6, p2, bottomLeft, topRight, bottomRight),
                        new TexturedTriangle(p1, p5, p6, bottomLeft, topLeft, topRight)
                },
                this
        );
        bottom = new Face(
                new TexturedTriangle[]{
                        //base
                        new TexturedTriangle(p1, p4, p3, topLeft, bottomLeft, bottomRight),
                        new TexturedTriangle(p2, p1, p3, topRight, topLeft, bottomRight)
                }, this
        );
        top = new Face(
                new TexturedTriangle[]{
                        //top
                        new TexturedTriangle(p5, p7, p6, bottomLeft, topRight, bottomRight),
                        new TexturedTriangle(p5, p8, p7, bottomLeft, topLeft, topRight)
                }, this
        );

        back = new Face(
                new TexturedTriangle[]{
                        //back
                        new TexturedTriangle(p3, p8, p4, bottomLeft, topRight, bottomRight),
                        new TexturedTriangle(p3, p7, p8, bottomLeft, topLeft, topRight)
                }, this
        );
        right = new Face(
                new TexturedTriangle[]{
                        //right
                        new TexturedTriangle(p2, p7, p3, bottomLeft, topRight, bottomRight),
                        new TexturedTriangle(p2, p6, p7, bottomLeft, topLeft, topRight)
                }, this
        );
        left = new Face(
                new TexturedTriangle[]{
                        //left
                        new TexturedTriangle(p4, p5, p1, bottomLeft, topRight, bottomRight),
                        new TexturedTriangle(p4, p8, p5, bottomLeft, topLeft, topRight)
                }, this
        );

        faces = new Face[]{
               right, left, top, bottom, back, front
        };




    }


    public void setAllSides(Texture texture){
        for(Face f: faces)
            f.setTexture(texture);
    }


    public Face getFront() {
        return front;
    }

    public Face getBack() {
        return back;
    }

    public Face getLeft() {
        return left;
    }

    public Face getRight() {
        return right;
    }

    public Face getBottom() {
        return bottom;
    }

    public Face getTop() {
        return top;
    }

    public void scaleFace(Face f, float scaleX, float scaleY){
        if(scaleX <= 0 || scaleY <= 0)
            throw new IllegalArgumentException("Scales must be > 0");

       for(TexturedTriangle t : f.getTriangles().toArray(TexturedTriangle[]::new)){
           t.setScaleX(scaleX);
           t.setScaleY(scaleY);
       }

       f.refreshTextureCoordinates();

    }


    @Override
    public void draw(World worldContext, float[] view, float[] projection) {
        for(Face f : faces){
            f.draw(worldContext, view, projection);  //just pass things through to the faces
        }
    }

    @Override
    public OpenGLVariableHolder getPositions() {
        return null;
    }
}
