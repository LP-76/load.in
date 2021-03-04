package com.example.loadin_app.ui.opengl;

import android.opengl.GLES20;

import java.util.stream.Stream;

public abstract class TexturedWorldObject extends  WorldObject {

    protected OpenGLVariableHolder textureCoordinates;

    public TexturedWorldObject(World world) {
        super(world);
    }

    @Override
    public OpenGLProgram getMyProgram() {
        return myWorld.getTextureViewProgram(); //this will use the texture view
    }

    protected abstract Stream<Float> getTextureCoordinates();

    @Override
    public void cleanupAfterDraw(OpenGLProgram program) {
        super.cleanupAfterDraw(program);
        program.disableVertexAttribute(textureCoordinates.getVariableName());
    }

    @Override
    public void uploadDataForShader(OpenGLProgram program) {

        uploadPositionInformation(program);  //load position data in
        uploadColorInformation(program);
        //we also need to upload the information for the texture positions
        textureCoordinates = new OpenGLVariableHolder(
                getTextureCoordinates(),
                2, World.TextureCoordinateProgram.A_TEX_COORD
        );
//
        program.setVertexAttributePointer(textureCoordinates, 2*4);  //2 coordinates per vertex
        //setup for texture

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0); //activate texture 0
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, getTexture().getHandle()); //tell it where the data is
        getMyProgram().setUniform1i(World.TextureCoordinateProgram.U_TEXTURE, 0);  //bind the variable to the shader so it can see it



    }

    public abstract Texture getTexture();

}
