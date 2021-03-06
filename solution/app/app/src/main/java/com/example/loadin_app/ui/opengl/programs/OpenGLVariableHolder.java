package com.example.loadin_app.ui.opengl.programs;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.stream.Stream;

public  class OpenGLVariableHolder {

    protected FloatBuffer buffer;
    protected int handle;
    protected int count;
    protected int coordinatesPerItem;


    public int getCoordinatesPerItem() {
        return coordinatesPerItem;
    }

    public FloatBuffer getBuffer() {
        return buffer;
    }



    public int getCount() {
        return count;
    }

    public OpenGLVariableHolder(float[] data, int coordinatesPerItem){

        this.coordinatesPerItem = coordinatesPerItem;
        // initialize vertex byte buffer for shape coordinates
       setData(data);;
    }
    public OpenGLVariableHolder(Stream<Float> data, int coordinatesPerItem){
        this.coordinatesPerItem = coordinatesPerItem;


        Float[] floats = data.toArray(Float[]::new);

        float[] results = new float[floats.length];
        for(int x = 0; x < floats.length; x++)
            results[x] = floats[x];
        setData(results);
    }

    private void setData(float[] data){
        count = data.length / coordinatesPerItem;
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                data.length * 4);
        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer
        buffer = bb.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        buffer.put(data);
        // set the buffer to read the first coordinate
        buffer.position(0);

    }

}
