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
    private  Triangle[] cubeMapTriangles;

    OpenGLVariableHolder positions;
    OpenGLVariableHolder colors;
    OpenGLVariableHolder texDirections;
    private CubeMap map;

    private Color myColor;

    public CubeMappedHexahedron(float width, float height, float length, IPlaceable parent){
        this(width, height, length, parent, new Color(1f,1f,1f,1f));

    }
    public CubeMappedHexahedron(float width, float height, float length, IPlaceable parent, Color color){
        super(width, height, length, parent);
        //IMPORTANT, the Z axis is considered horizontal and Y is considered the up direction
        myColor = color;
        setupTriangles(width, height, length);
        setupVariables();
    }

    private void setupTriangles(float width,  float height,float length){
        float x = width;
        float z = length;
        float y = height;


        Vector p1 = new Vector(0, 0, 0, myColor);
        Vector p2 = new Vector(x, 0, 0, myColor);
        Vector p3 = new Vector(x, 0, z, myColor);
        Vector p4 = new Vector(0, 0, z, myColor);
        Vector p5 = new Vector(0, y, 0, myColor);
        Vector p6 = new Vector(x, y, 0f, myColor);
        Vector p7 = new Vector(x, y, z, myColor);
        Vector p8 = new Vector(0, y, z, myColor);


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

        Vector c1 = new Vector(0, 0, 0, myColor);
        Vector c2 = new Vector(1, 0, 0, myColor);
        Vector c3 = new Vector(1, 0, 1, myColor);
        Vector c4 = new Vector(0, 0, 1, myColor);
        Vector c5 = new Vector(0, 1, 0, myColor);
        Vector c6 = new Vector(1, 1, 0f, myColor);
        Vector c7 = new Vector(1, 1, 1, myColor);
        Vector c8 = new Vector(0, 1, 1, myColor);


        cubeMapTriangles = new Triangle[] {
                //front
                new Triangle(c1, c6, c2 ),
                new Triangle(c1, c5, c6),
                //base
                new Triangle(c1, c4, c3),
                new Triangle(c2, c1, c3),
                //top
                new Triangle(c5, c7, c6),
                new Triangle(c5, c8, c7),
                //back
                new Triangle(c3, c8, c4),
                new Triangle(c3, c7, c8),
                //right
                new Triangle(c2, c7, c3),
                new Triangle(c2, c6, c7),
                //left
                new Triangle(c4, c5, c1),
                new Triangle(c4, c8, c5)

        };


    }

    public Color getMyColor() {
        return myColor;
    }

    public void setMyColor(Color myColor) {
        this.myColor = myColor;
        setupTriangles(width, height, length );
        setupVariables();
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
                1f/2f,
                1f/2f,
                1f/2f
        ).multiply(-1f);  //reverse direction

        texDirections = new OpenGLVariableHolder(
                Arrays.stream(cubeMapTriangles).map(i -> i.moveAndCopy(center)).flatMap(i -> i.getCoordinates()),
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
