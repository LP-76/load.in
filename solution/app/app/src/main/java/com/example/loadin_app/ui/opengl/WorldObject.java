package com.example.loadin_app.ui.opengl;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.stream.Stream;

/**
 * An object that can be displayed in the world
 */
public abstract class  WorldObject implements IConvertToFloat {

    private Vector offset;


    private final String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    // the matrix must be included as a modifier of gl_Position
                    // Note that the uMVPMatrix factor *must be first* in order
                    // for the matrix multiplication product to be correct.
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";
    private int vPMatrixHandle;


    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    private int positionHandle;
    private int colorHandle;

    private  int vertexCount;
    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex


    private FloatBuffer vertexBuffer;
    private final int program;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;

    // Set color with red, green, blue and alpha (opacity) values
    float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };

    private float[] objectCoords;

    public  WorldObject(){


        offset = new Vector(0,0,0);


        int vertexShader = TestGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = TestGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        // create empty OpenGL ES Program
        program = GLES20.glCreateProgram();

        // add the vertex shader to program
        GLES20.glAttachShader(program, vertexShader);

        // add the fragment shader to program
        GLES20.glAttachShader(program, fragmentShader);

        // creates OpenGL ES program executables
        GLES20.glLinkProgram(program);
    }

    public void place(Vector offset){
        this.offset  = offset;
    }

    public void move(Vector direction){
        this.offset = new Vector(offset.getX() + direction.getX(),
                offset.getY() + direction.getY(),
                offset.getZ() + direction.getZ()
        );
    }

    private void loadBuffer(){
        objectCoords = asFloatArray();





        vertexCount = objectCoords.length / COORDS_PER_VERTEX;
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                objectCoords.length * 4);
        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer
        vertexBuffer = bb.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        vertexBuffer.put(objectCoords);
        // set the buffer to read the first coordinate
        vertexBuffer.position(0);
    }

    public abstract Stream<Shape> getShapes();

    public Stream<Float> asFloats(){
        return getShapes().flatMap(i -> i.getTriangles()).flatMap(i -> i.asFloats());
    }

    public float[] asFloatArray(){

        Float[] floats = asFloats().toArray(Float[]::new);

        float[] results = new float[floats.length];
        for(int x = 0; x < floats.length; x++)
            results[x] = floats[x];
        return results;
    }

    public void draw(float[] mvpMatrix) {

        loadBuffer();

        //apply translations here

        float[] translationMatrix = new float[16];

        //set the identity matrix for translation
        Matrix.setIdentityM(translationMatrix, 0);

        //add the translation to the matrix
        Matrix.translateM(translationMatrix,0, offset.getX(), offset.getY(), offset.getZ() );

        float[] postTranslationMatrix = new float[16];
        //apply the translation to the objects matrix translation
        Matrix.multiplyMM(postTranslationMatrix, 0, mvpMatrix, 0, translationMatrix, 0);


        // Add program to OpenGL ES environment
        GLES20.glUseProgram(program);

        // get handle to vertex shader's vPosition member
        positionHandle = GLES20.glGetAttribLocation(program, "vPosition");

        // Enable a handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(positionHandle);

        // Prepare the triangle coordinate data
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        colorHandle = GLES20.glGetUniformLocation(program, "vColor");

        // Set color for drawing the triangle
        GLES20.glUniform4fv(colorHandle, 1, color, 0);

        // get handle to shape's transformation matrix
        vPMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix");

        // Pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(vPMatrixHandle, 1, false, postTranslationMatrix, 0);


        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);


        // Disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle);
    }

}
