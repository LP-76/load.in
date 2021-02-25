package com.example.loadin_app.ui.opengl;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

public class Texture {
    //a wrapper class for loading a texture

    private final int[] handle = new int[1];
    private final Bitmap source;

    public Texture( Bitmap source, OpenGLProgram program, String variableName){

        GLES20.glGenTextures(1, handle, 0);
        this.source = source;

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, handle[0]);  //bind to the handle
        checkError();

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);  //set repeating
        checkError();
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
        checkError();

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        checkError();
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        checkError();

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, source, 0);  //send the image to this newly bound texture
        checkError();
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        checkError();
        if(handle[0] == 0)
            throw new RuntimeException("Did not load texture as expected");



    }

    private void checkError(){
        int error = GLES20.glGetError();
        if(error != 0)
            throw new RuntimeException("An unknown error has occurred: " + error);
    }

    public int getHandle(){
        return handle[0];
    }

}
