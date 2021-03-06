package com.example.loadin_app.ui.opengl;

import android.opengl.GLES20;

import java.util.Arrays;
import java.util.stream.Stream;

public class CubeMappedHexahedron extends Hexahedron implements IDrawable {

    private  Triangle[] triangles;

    OpenGLVariableHolder positions;
    OpenGLVariableHolder colors;
    OpenGLVariableHolder texDirections;
    private CubeMap map;


    public CubeMappedHexahedron(float width, float height, float length){
        super(width, height, length);


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
                3, World.CubeMapProgram.A_POSITION
        );
        colors = new OpenGLVariableHolder(
                Arrays.stream(triangles).flatMap(i -> i.getColors()),
                4, World.CubeMapProgram.A_COLOR
        );
        Vector center = new Vector(
                getWidth()/2f,
                getHeight()/2f,
                getLength()/2f
        ).multiply(-1f);  //reverse direction

//        for(Triangle t: triangles)
//            t.move(center);  //offset all vectors around the center

        texDirections = new OpenGLVariableHolder(
                Arrays.stream(triangles).map(i -> i.moveAndCopy(center)).flatMap(i -> i.getCoordinates()),
                3, World.CubeMapProgram.A_TEX_DIRECTION
        );
    }


    @Override
    public void move(Vector direction) {
          for(Triangle t : triangles){
                t.move(direction);
            }
    }

    @Override
    public Stream<Triangle> getTriangles() {
        return Arrays.stream( triangles);
    }


    @Override
    public void draw(World worldContext) {
        World.CubeMapProgram program = worldContext.getCubeMapProgram();
        GLES20.glUseProgram(program.getProgramHandle()); //set this the active program

        //upload position information
        program.setVertexAttributePointer(positions, 3*4);  //conduct upload

        //upload color information
        program.setVertexAttributePointer(colors, 4*4);

        //upload texture information
        program.setVertexAttributePointer(texDirections, 3*4);

        //bind the cubemap
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);//I think this tells the program to bind the the variable for the cube
        GLES20.glBindTexture(GLES20.GL_TEXTURE_CUBE_MAP, map.getHandle());
        program.setUniform1i(World.CubeMapProgram.U_CUBE, 0);




        //conduct the render
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, positions.getCount());

        //cleanup
        program.disableVertexAttribute(World.CubeMapProgram.A_TEX_DIRECTION);
        program.disableVertexAttribute(World.CubeMapProgram.A_COLOR);
        program.disableVertexAttribute(World.CubeMapProgram.A_POSITION);
    }

    public CubeMap getMap() {
        return map;
    }

    public void setMap(CubeMap map) {
        this.map = map;
    }
}
