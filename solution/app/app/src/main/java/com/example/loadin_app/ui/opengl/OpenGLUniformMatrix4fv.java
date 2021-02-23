package com.example.loadin_app.ui.opengl;

import android.opengl.GLES20;

import java.util.stream.Stream;

public class OpenGLUniformMatrix4fv extends OpenGLVariableLoader {

    public OpenGLUniformMatrix4fv(float[] data, int coordinatesPerItem, String variableName) {
        super(data, coordinatesPerItem, variableName);
    }

    public OpenGLUniformMatrix4fv(Stream<Float> data, int coordinatesPerItem, String variableName) {
        super(data, coordinatesPerItem, variableName);
    }

    @Override
    public void establishHandle(int programHandle) {
        handle = GLES20.glGetUniformLocation(programHandle, variableName);
    }

    @Override
    public void uploadData() {

        GLES20.glUniformMatrix4fv(handle,count, false, buffer);
    }
}
