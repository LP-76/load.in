package com.example.loadin_app.ui.opengl;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.example.loadin_app.ui.opengl.programs.OpenGLProgram;

public class Texture {
    //a wrapper class for loading a texture

    private final int[] handle = new int[1];
    private final Bitmap source;

    private int sourceWidth;
    private  int sourceHeight;

    public Texture(Bitmap source, OpenGLProgram program, String variableName, boolean generateMipmap){

        GLES20.glGenTextures(1, handle, 0);
        this.source = source;

        if(handle[0] != 0){
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, handle[0]);  //bind to the handle
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

           // GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);  //set repeating

           // GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);


            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, source, 0);  //send the image to this newly bound texture
            if(generateMipmap)
                GLES20.glGenerateMipmap(handle[0]);
            //checkError();
           // GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        }
        else
            throw new RuntimeException("Did not load texture as expected");

        sourceHeight = source.getHeight();
        sourceWidth = source.getWidth();

    }

    public int getSourceWidth() {
        return sourceWidth;
    }

    public int getSourceHeight() {
        return sourceHeight;
    }

    private void checkError(){
        int error = GLES20.glGetError();
        if(error != 0)
            throw new RuntimeException("An unknown error has occurred: " + error);
    }

    public int getHandle(){
        return handle[0];
    }

    public void delete(){  //delete the texture from memory when no longer needed
       GLES20.glDeleteTextures(1, handle, 0);
       GLES20.glFlush();
    }


}
