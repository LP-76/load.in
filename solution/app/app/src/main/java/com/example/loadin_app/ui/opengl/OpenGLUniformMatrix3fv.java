package com.example.loadin_app.ui.opengl;

import android.opengl.GLES20;

import java.util.stream.Stream;

public class OpenGLUniformMatrix3fv extends OpenGLVariableLoader {
    public OpenGLUniformMatrix3fv(float[] data, int coordinatesPerItem, String variableName) {
        super(data, coordinatesPerItem, variableName);
    }

    public OpenGLUniformMatrix3fv(Stream<Float> data, int coordinatesPerItem, String variableName) {
        super(data, coordinatesPerItem, variableName);
    }

    @Override
    public void establishHandle(int programHandle) {
        handle = GLES20.glGetUniformLocation(programHandle, variableName);
    }

    @Override
    public void uploadData() {
        GLES20.glUniform3fv(handle, count, buffer);

    }
}
