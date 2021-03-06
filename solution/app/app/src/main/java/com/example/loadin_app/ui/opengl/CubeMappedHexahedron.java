package com.example.loadin_app.ui.opengl;

import android.opengl.GLES20;

import com.example.loadin_app.ui.opengl.programs.IColorable;
import com.example.loadin_app.ui.opengl.programs.ICubeMappable;
import com.example.loadin_app.ui.opengl.programs.IPlaceable;
import com.example.loadin_app.ui.opengl.programs.OpenGLVariableHolder;

import java.util.Arrays;
import java.util.stream.Stream;

public class CubeMappedHexahedron extends Hexahedron implements IColorable, ICubeMappable {

    private  Triangle[] triangles;

    OpenGLVariableHolder positions;
    OpenGLVariableHolder colors;
    OpenGLVariableHolder texDirections;
    private CubeMap map;


    public CubeMappedHexahedron(float width, float height, float length, IPlaceable parent){
        super(width, height, length, parent);


        //IMPORTANT, the Z axis is considered horizontal and Y is considered the up direction

       setupTriangles(width, height, length);
        setupVariables();
    }

    private void setupTriangles(float width,  float height,float length){
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


        triangles = new Triangle[] {
                //front
                new Triangle(p1, p6, p2 ),
                new Triangle(p1, p5, p6),
                //base
                new Triangle(p1, p4, p3),
                new Triangle(p2, p1, p3),
                //top
                new Triangle(p5, p7, p6),
                new Triangle(p5, p8, p7),
                //back
                new Triangle(p3, p8, p4),
                new Triangle(p3, p7, p8),
                //right
                new Triangle(p2, p7, p3),
                new Triangle(p2, p6, p7),
                //left
                new Triangle(p4, p5, p1),
                new Triangle(p4, p8, p5)

        };


    }

    private void setupVariables(){
        positions = new OpenGLVariableHolder(
                Arrays.stream(triangles).flatMap(i -> i.getCoordinates()),
                3
        );
        colors = new OpenGLVariableHolder(
                Arrays.stream(triangles).flatMap(i -> i.getColors()),
                4
        );
        Vector center = new Vector(
                getWidth()/2f,
                getHeight()/2f,
                getLength()/2f
        ).multiply(-1f);  //reverse direction

        texDirections = new OpenGLVariableHolder(
                Arrays.stream(triangles).map(i -> i.moveAndCopy(center)).flatMap(i -> i.getCoordinates()),
                3
        );
    }


    @Override
    public OpenGLVariableHolder getCubeTextureDirections() {
        return texDirections;
    }

    public CubeMap getMap() {
        return map;
    }

    public void setMap(CubeMap map) {
        this.map = map;
    }

    @Override
    public void draw(World worldContext, float[] view, float[] projection) {
        worldContext.getCubeMapProgram().render(this, view, projection);
    }

    @Override
    public OpenGLVariableHolder getPositions() {
      return positions;
    }

    @Override
    public OpenGLVariableHolder getColors() {
        return colors;
    }
}
