package com.example.loadin_app.ui.opengl;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLUtils;

import com.example.loadin_app.ui.opengl.programs.OpenGLProgram;

public class CubeMap {
    public static final int RIGHT = GLES20.GL_TEXTURE_CUBE_MAP_POSITIVE_X;
    public static final int LEFT = GLES20.GL_TEXTURE_CUBE_MAP_NEGATIVE_X;
    public static final int TOP = GLES20.GL_TEXTURE_CUBE_MAP_POSITIVE_Y;
    public static final int BOTTOM = GLES20.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y;
    public static final int BACK = GLES20.GL_TEXTURE_CUBE_MAP_POSITIVE_Z;
    public static final int FRONT = GLES20.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z;



    private int[] handle = new int[1];
    public CubeMap(OpenGLProgram program){
        GLES30.glGenTextures(1, handle,0);
        if(handle[0] < 0)
            throw new RuntimeException("Unable to generate new handle for cubemap");
        GLES20.glBindTexture(GLES20.GL_TEXTURE_CUBE_MAP, handle[0]);




    }

    public int getHandle(){
        return handle[0];
    }

    public void addTexture(Bitmap source, int face){

        GLUtils.texImage2D(
            face,
                0,  source, 0
        );
    }

    public void applyParamaters(){
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_CUBE_MAP, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_CUBE_MAP, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_CUBE_MAP, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_MIRRORED_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_CUBE_MAP, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_MIRRORED_REPEAT);
    }

}
