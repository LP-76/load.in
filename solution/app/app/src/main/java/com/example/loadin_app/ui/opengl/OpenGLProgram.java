package com.example.loadin_app.ui.opengl;

import android.opengl.GLES20;

public abstract class OpenGLProgram {
    private int programHandle;

    public OpenGLProgram(){
        programHandle = GLES20.glCreateProgram();
    }

    public int getProgramHandle(){
        return programHandle;
    }

    public abstract  void load();

    protected static int loadShader(int type, String shaderCode){

        int shaderHandle = GLES20.glCreateShader(type);

        if (shaderHandle != 0)
        {
            // Pass in the shader source.
            GLES20.glShaderSource(shaderHandle, shaderCode);

            // Compile the shader.
            GLES20.glCompileShader(shaderHandle);

            // Get the compilation status.
            final int[] compileStatus = new int[1];
            GLES20.glGetShaderiv(shaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

            // If the compilation failed, delete the shader.
            if (compileStatus[0] == 0)
            {
                // Log.e(TAG, "Error compiling shader: " + GLES20.glGetShaderInfoLog(shaderHandle));
                GLES20.glDeleteShader(shaderHandle);
                shaderHandle = 0;
            }
        }

        if (shaderHandle == 0)
        {
            throw new RuntimeException("Error creating shader.");
        }

        return shaderHandle;
    }

}
