package com.example.loadin_app.ui.opengl;

import android.opengl.GLES20;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.stream.Stream;

public class TexturedHexahedron extends Hexahedron implements IDrawable{


    private Face[] faces;

    private Face front;
    private Face back;
    private Face left;
    private Face right;
    private Face bottom;
    private Face top;

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

        front = new Face(
                new TexturedTriangle[]{
                        //front
                        new TexturedTriangle(p1, p6, p2, bottomLeft, topRight, bottomRight),
                        new TexturedTriangle(p1, p5, p6, bottomLeft, topLeft, topRight)
                }
        );
        bottom = new Face(
                new TexturedTriangle[]{
                        //base
                        new TexturedTriangle(p1, p4, p3, topLeft, bottomLeft, bottomRight),
                        new TexturedTriangle(p2, p1, p3, topRight, topLeft, bottomRight)
                }
        );
        top = new Face(
                new TexturedTriangle[]{
                        //top
                        new TexturedTriangle(p5, p7, p6, bottomLeft, topRight, bottomRight),
                        new TexturedTriangle(p5, p8, p7, bottomLeft, topLeft, topRight)
                }
        );

        back = new Face(
                new TexturedTriangle[]{
                        //back
                        new TexturedTriangle(p3, p8, p4, bottomLeft, topRight, bottomRight),
                        new TexturedTriangle(p3, p7, p8, bottomLeft, topLeft, topRight)
                }
        );
        right = new Face(
                new TexturedTriangle[]{
                        //right
                        new TexturedTriangle(p2, p7, p3, bottomLeft, topRight, bottomRight),
                        new TexturedTriangle(p2, p6, p7, bottomLeft, topLeft, topRight)
                }
        );
        left = new Face(
                new TexturedTriangle[]{
                        //left
                        new TexturedTriangle(p4, p5, p1, bottomLeft, topRight, bottomRight),
                        new TexturedTriangle(p4, p8, p5, bottomLeft, topLeft, topRight)
                }
        );

        faces = new Face[]{
                front, top, right, left, bottom, back
        };




    }



    public void setFrontTexture(Texture front) {
        this.front.texture = front;
    }
    public void setBackTexture(Texture back) {
        this.back.texture = back;
    }
    public void setLeftTexture(Texture left) {
        this.left.texture = left;
    }
    public void setRightTexture(Texture right) {
        this.right.texture = right;
    }
    public void setBottomTexture(Texture bottom) {
        this.bottom.texture = bottom;
    }
    public void setTopTexture(Texture top) {
        this.top.texture = top;
    }

    @Override
    public void move(Vector direction) {
        for(Face f: faces)
            for(Triangle t : f.triangles){
                t.move(direction);
            }
    }

    @Override
    public Stream<Triangle> getTriangles() {
        return Arrays.stream( faces).flatMap(i -> Arrays.stream(i.triangles));
    }

    public Stream<TexturedTriangle> getTexturedTriangles(){
        return Arrays.stream( faces).flatMap(i -> Arrays.stream(i.triangles));
    }

    @Override
    public void draw(World worldContext) {
        OpenGLProgram program = worldContext.getTextureViewProgram();
        GLES20.glUseProgram(program.getProgramHandle()); //set this the active program

        Face[] faces = {front, back, left, right, bottom, top};

        for(Face f: faces){

            //upload position information

            program.setVertexAttributePointer(f.positions, 3&4);  //conduct upload

            //upload color information
            program.setVertexAttributePointer(f.colors, 4*4);

            //upload texture information
            program.setVertexAttributePointer(f.textureCoordinates, 2*4);

            //set the texture
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, f.texture.getHandle());  //bind to the handle

            //draw
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, f.positions.getCount());

            //cleanup
            program.disableVertexAttribute(World.TextureCoordinateProgram.A_TEX_COORD);
            program.disableVertexAttribute(World.TextureCoordinateProgram.A_COLOR);
            program.disableVertexAttribute(World.TextureCoordinateProgram.A_POSITION);

        }




    }

    public class Face{
        private TexturedTriangle[] triangles;
        private Texture texture;

        OpenGLVariableHolder positions;
        OpenGLVariableHolder colors;
        OpenGLVariableHolder textureCoordinates;




        public Face(TexturedTriangle[] triangles){
            this.triangles = triangles;
             positions = new OpenGLVariableHolder(
                    Arrays.stream(triangles).flatMap(i -> i.getCoordinates()),
                    3, World.TextureCoordinateProgram.A_POSITION
            );
             colors = new OpenGLVariableHolder(
                    Arrays.stream(triangles).flatMap(i -> i.getColors()),
                    4, World.TextureCoordinateProgram.A_COLOR
            );
            textureCoordinates = new OpenGLVariableHolder(
                    Arrays.stream(triangles).flatMap(i -> i.getTextureCoordinates()),
                    2, World.TextureCoordinateProgram.A_TEX_COORD
            );
        }

    }

}
