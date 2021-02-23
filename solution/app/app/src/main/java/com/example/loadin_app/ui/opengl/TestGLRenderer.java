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
    private  float[] viewMatrix = new float[16];
    private float[] rotationMatrix = new float[16];

    public volatile float angle;

    public float getAngle() {
        return angle;
    }

    public Camera getTheCamera(){
        return theCamera;
    }

    private World theWorld;
    private Camera theCamera;
    public TestGLRenderer(){


    }

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color


        GLES20.glEnable(GLES20.GL_DEPTH_TEST);


        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        theWorld = new World();
        theCamera = new Camera();
        theCamera.placeCamera(new Vector(0f, 0f, 2f));



    }

    public void onDrawFrame(GL10 unused) {
      //  float[] scratch = new float[16];
        GLES20.glClearColor(109f/255f, 209f/255f, 161f/255f, 1f);
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT);


        viewMatrix = theCamera.getLookatMatrix();

        // Calculate the projection and view transformation
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);


        //GLTriangleExample triangle = new GLTriangleExample();
        //triangle.draw(vPMatrix);

        Truck t = new Truck(theWorld);
        t.move(new Vector(3f, 0f, 3f));
        t.draw(vPMatrix, viewMatrix);

        Box test = new Box(24f,24f,24f, theWorld); //a 2 foot box
        test.place(new Vector(t.getWidthInches() - 24f, 0f, t.getLengthInches() - 24f));
        test.draw(vPMatrix, viewMatrix);
//
       // Box test2 = new Box(0.6f,0.3f,0.9f, theWorld);
       // test2.place(new Vector(-0.5f, -0.5f, 0));
       // test2.draw(vPMatrix, viewMatrix);


    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1f, 1f, 1f, 100f);



    }





}
