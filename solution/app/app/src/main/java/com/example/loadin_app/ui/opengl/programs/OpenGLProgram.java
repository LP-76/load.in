package com.example.loadin_app.ui.opengl.programs;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.example.loadin_app.ui.opengl.Vector;
import com.example.loadin_app.ui.opengl.World;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class OpenGLProgram {
    private int programHandle;

    public OpenGLProgram(){

        programHandle = GLES20.glCreateProgram();
    }

    public int getProgramHandle(){
        return programHandle;
    }


    protected  static String loadShaderFile(Context context, int resId){
        InputStream in = context.getResources().openRawResource(resId);
        InputStreamReader inReader = new InputStreamReader(in);
        BufferedReader reader = new BufferedReader(inReader);
        StringBuilder text = new StringBuilder();
        String line;
        try{
            while((line = reader.readLine())!= null)
                text.append(line + "\n");
        }catch(IOException ex){
            return null;
        }
        return text.toString();
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

   public abstract void load(Context context);

    public void setUniform1i(String variableName, int value){
        int handle = getUniformHandle(variableName);
        GLES20.glUniform1i(handle, value);
    }



    public void setUniformMatrix3fv(OpenGLVariableHolder data, String variableName){
        int handle = getUniformHandle(variableName);
        GLES20.glUniform3fv(handle, data.getCount(), data.getBuffer());
    }

    public void setUniformMatrix4fv(float[] data, String variableName){
        int handle = getUniformHandle(variableName);
        GLES20.glUniformMatrix4fv(handle,1, false, data, 0);
    }

    public void setVertexAttributePointer(OpenGLVariableHolder data, String variableName){
        int handle = getAttributeHandle(variableName);
        GLES20.glEnableVertexAttribArray(handle);
        GLES20.glVertexAttribPointer(handle, data.getCoordinatesPerItem(), GLES20.GL_FLOAT, false,
                data.getCoordinatesPerItem() * 4, data.getBuffer());
    }

    public void disableVertexAttribute(String variableName){
        int handle = getAttributeHandle(variableName);
        GLES20.glDisableVertexAttribArray(handle);
    }

    protected <T extends IPlaceable> float[] processTranslation(T item){

        //apply translations here

        Vector worldOffset = item.getWorldOffset();

        float[] translationMatrix = new float[16];

        //set the identity matrix for translation
        Matrix.setIdentityM(translationMatrix, 0);
        //add the translation to the matrix
        Matrix.translateM(translationMatrix,0, worldOffset.getX(), worldOffset.getY(), worldOffset.getZ() );

        return translationMatrix;
    }

    protected float[] processScale(Vector scale){
        float[] scaleMatrix = new float[16];

        //set the identity matrix for translation
        Matrix.setIdentityM(scaleMatrix, 0);

        //add the translation to the matrix
        Matrix.scaleM(scaleMatrix,0, scale.getX(),  scale.getY(), scale.getZ());

        return scaleMatrix;
    }

    protected  float[] processRotation(IRotatable item){
        float yaw = item.getYaw();
        float pitch = item.getPitch();
        return processRotation(yaw, pitch);
    }
    protected float[] processRotation(float yaw, float pitch){
        float[] rotationMatrix = new float[16];
        Matrix.setIdentityM(rotationMatrix, 0);

        Matrix.rotateM(rotationMatrix, 0, yaw, 0f, 1f, 0f);
        Matrix.rotateM(rotationMatrix, 0, pitch, 1f, 0f, 0f);

        return rotationMatrix;
    }


}
