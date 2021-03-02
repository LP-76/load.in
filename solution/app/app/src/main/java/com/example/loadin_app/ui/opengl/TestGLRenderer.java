package com.example.loadin_app.ui.opengl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class TestGLRenderer implements GLSurfaceView.Renderer {
    public static final Color LOAD_IN_GREEN = new Color(109, 209, 161, 1);
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
    private Bitmap testBitmap;
    public TestGLRenderer(Bitmap source){
        testBitmap = source;

    }

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color


        GLES20.glEnable(GLES20.GL_DEPTH_TEST);


        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        theWorld = new World();
        theCamera = new Camera();



        Truck t = new Truck(theWorld);
        t.move(new Vector(3f, 0f, 3f));

        Box test = new Box(24f,24f,24f, theWorld); //a 2 foot box
        test.place(new Vector(t.getWidthInches() - 24f, 0f, t.getLengthInches() - 24f));

        Box test2= new Box(48f,48f,24f, theWorld); //a 2 foot box
        test2.place(new Vector(32f, 0f, 32f));

        Sign testSign = new Sign(theWorld, 12f, 12f);
        //testSign.setMessage("Hello cruel world");
        testSign.testBitmap(testBitmap);
        testSign.place(new Vector(1f,1f, 1f));

        theCamera.focusOn(test2.getOffset(), 2f * 12f);  //focus at the base of the sign

        //theCamera.placeCamera(new Vector(1f, 2f, -2f));
        //theCamera.lookAt(new Vector(1f, 1f, 1f));  //test looking at the truck right corner
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

        for(WorldObject wo: theWorld.getWorldObjects()){
            wo.draw(vPMatrix, viewMatrix);
        }

//


    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1f, 1f, 1f, 100f);



    }





}
