package com.example.loadin_app.ui.opengl;

import android.opengl.GLES20;

import java.util.stream.Stream;

public class OpenGLVertexAttribute extends OpenGLVariableLoader {
    private int stride;

    public OpenGLVertexAttribute(float[] data, int coordinatesPerItem, String variableName, int stride) {
        super(data, coordinatesPerItem, variableName);
        this.stride = stride;
    }

    public OpenGLVertexAttribute(Stream<Float> data, int coordinatesPerItem, String variableName, int stride) {
        super(data, coordinatesPerItem, variableName);
        this.stride = stride;
    }

    @Override
    public void establishHandle(int programHandle) {
        handle = GLES20.glGetAttribLocation(programHandle, variableName);
    }

    @Override
    public void uploadData() {
        GLES20.glVertexAttribPointer(handle, coordinatesPerItem, GLES20.GL_FLOAT, false,
                stride, buffer);
    }
}
