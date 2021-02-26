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

    private int getUniformHandle(String variableName){
        int handle = GLES20.glGetUniformLocation(programHandle, variableName);
        if(handle < 0)
            throw new RuntimeException("Missing handle for variable: " + variableName);
        return handle;
    }
    private int getAttributeHandle(String variableName){
        int handle = GLES20.glGetAttribLocation(programHandle, variableName);
        if(handle < 0)
            throw new RuntimeException("Missing handle for variable: " + variableName);
        return handle;
    }

   public abstract void load();

    public void setUniform1i(String variableName, int value){
        int handle = getUniformHandle(variableName);
        GLES20.glUniform1i(handle, value);
    }



    public void setUniformMatrix3fv(OpenGLVariableHolder data){
        int handle = getUniformHandle(data.getVariableName());
        GLES20.glUniform3fv(handle, data.getCount(), data.getBuffer());
    }

    public void setUniformMatrix4fv(OpenGLVariableHolder data){
        int handle = getUniformHandle(data.getVariableName());
        GLES20.glUniformMatrix4fv(handle,data.getCount(), false, data.getBuffer());
    }

    public void setVertexAttributePointer(OpenGLVariableHolder data, int stride){
        int handle = getAttributeHandle(data.getVariableName());
        GLES20.glEnableVertexAttribArray(handle);
        GLES20.glVertexAttribPointer(handle, data.getCoordinatesPerItem(), GLES20.GL_FLOAT, false,
                stride, data.getBuffer());
    }

    public void disableVertexAttribute(String variableName){
        int handle = getAttributeHandle(variableName);
        GLES20.glDisableVertexAttribArray(handle);
    }

}
