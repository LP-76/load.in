package com.example.loadin_app.ui.opengl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class TestGLRenderer implements GLSurfaceView.Renderer {

    private final float[] vPMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private float[] rotationMatrix = new float[16];

    public volatile float angle;

    public float getAngle() {
        return angle;
    }

    public void setAngle(float p_angle) {
        angle = p_angle;
    }


    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);



    }

    public void onDrawFrame(GL10 unused) {
      //  float[] scratch = new float[16];

        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);



        // Set the camera position (View matrix)
        Matrix.setLookAtM(viewMatrix, 0, -1, -1, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);


        //Matrix.setRotateM(rotationMatrix, 0, angle, 0, 0, -1.0f);
        //experiment, what happens when we apply the rotation to the viewMatrix
        //Matrix.multiplyMM(scratch, 0, vPMatrix, 0, rotationMatrix, 0);

        // Create a rotation transformation for the triangle
        long time = SystemClock.uptimeMillis() % 4000L;
        //float angle = 0.090f * ((int) time);
        //Matrix.setRotateM(rotationMatrix, 0, angle, 0, 0, -1.0f);

        // Combine the rotation matrix with the projection and camera view
        // Note that the vPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
      //  Matrix.multiplyMM(scratch, 0, vPMatrix, 0, rotationMatrix, 0);



        //GLTriangleExample test = new GLTriangleExample();
        //test.draw(scratch);

        Box test = new Box(0.3f,0.3f,0.3f);
        test.draw(vPMatrix);

        Box test2 = new Box(0.6f,0.3f,0.9f);
        test2.place(new Vector(-0.5f, -0.5f, 0));
        test2.draw(vPMatrix);


    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);



    }

    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }



}
