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

    private World theWorld;

    public TestGLRenderer(){


    }

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);



    }

    public void onDrawFrame(GL10 unused) {
      //  float[] scratch = new float[16];
        if(theWorld == null)
             theWorld = new World();
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);



        // Set the camera position (View matrix)
        Matrix.setLookAtM(viewMatrix, 0, -1, -1, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);


        //GLTriangleExample triangle = new GLTriangleExample();
        //triangle.draw(vPMatrix);

        Box test = new Box(0.3f,0.3f,0.3f, theWorld);
        test.draw(vPMatrix, viewMatrix);
//
        Box test2 = new Box(0.6f,0.3f,0.9f, theWorld);
        test2.place(new Vector(-0.5f, -0.5f, 0));
        test2.draw(vPMatrix, viewMatrix);


    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 20);



    }





}
