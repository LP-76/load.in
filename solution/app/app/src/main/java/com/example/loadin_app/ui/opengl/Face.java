package com.example.loadin_app.ui.opengl;

import android.opengl.GLES20;

import java.util.Arrays;
import java.util.stream.Stream;

public class Face extends Shape implements IDrawable{
    private TexturedTriangle[] triangles;
    private Texture texture;

    private OpenGLVariableHolder positions;
    private OpenGLVariableHolder colors;
    private OpenGLVariableHolder textureCoordinates;

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public OpenGLVariableHolder getPositions() {
        return positions;
    }

    public OpenGLVariableHolder getColors() {
        return colors;
    }

    public OpenGLVariableHolder getTextureCoordinates() {
        return textureCoordinates;
    }

    public Face(TexturedTriangle[] triangles){
        this.triangles = triangles;

        colors = new OpenGLVariableHolder(
                Arrays.stream(triangles).flatMap(i -> i.getColors()),
                4, World.TextureCoordinateProgram.A_COLOR
        );

        refreshPositions();
        refreshTextureCoordinates();
    }

    public void refreshTextureCoordinates(){
        textureCoordinates = new OpenGLVariableHolder(
                Arrays.stream(triangles).flatMap(i -> i.getTextureCoordinates()),
                2, World.TextureCoordinateProgram.A_TEX_COORD
        );
    }
    public void refreshPositions(){
        positions = new OpenGLVariableHolder(
                Arrays.stream(triangles).flatMap(i -> i.getCoordinates()),
                3, World.TextureCoordinateProgram.A_POSITION
        );
    }

    @Override
    public void move(Vector direction) {
        for(Triangle t: triangles)
            t.move(direction);
        refreshPositions();
    }

    @Override
    public Stream<Triangle> getTriangles() {
        return Arrays.stream(triangles);
    }

    public Stream<TexturedTriangle> getTexturedTriangles() {
        return Arrays.stream(triangles);
    }

    @Override
    public void draw(World worldContext) {
        //upload position information
        OpenGLProgram program = worldContext.getTextureViewProgram();


        program.setVertexAttributePointer(getPositions(), 3*4);  //conduct upload

        //upload color information
        program.setVertexAttributePointer(getColors(), 4*4);

        //upload texture information
        program.setVertexAttributePointer(getTextureCoordinates(), 2*4);

        //set the texture
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, getTexture().getHandle());  //bind to the handle

        //draw
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, getPositions().getCount());

        //cleanup
        program.disableVertexAttribute(World.TextureCoordinateProgram.A_TEX_COORD);
        program.disableVertexAttribute(World.TextureCoordinateProgram.A_COLOR);
        program.disableVertexAttribute(World.TextureCoordinateProgram.A_POSITION);
    }
}
