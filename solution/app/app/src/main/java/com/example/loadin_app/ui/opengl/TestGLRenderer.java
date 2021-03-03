package com.example.loadin_app.ui.opengl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import com.example.loadin_app.LoadPlan;
import com.example.loadin_app.TestingLoadPlanGenerator;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;

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

    private LoadPlan theLoadPlan;

    private Vector boxStagingArea;

    private Box currentBox;
    private  int step;
    private boolean advanceInProgress;

    public TestGLRenderer(Bitmap source){
        testBitmap = source;
        step = 0;
        advanceInProgress = false;
    }

    public void advance(){
        if(advanceInProgress)
            return;

        step++;
        if(step % 2 == 0){
            //put the box into staging
            if(theLoadPlan.GetCurrentLoad().HasNextBox())
                theLoadPlan.GetCurrentLoad().GetNextBox();
            putNextBoxIntoStaging();
        }else{
            moveStagedBoxIntoPosition();
        }



    }


    private  void putNextBoxIntoStaging(){

            currentBox = theLoadPlan.GetCurrentLoad().GetCurrentBox();
            if(currentBox != null){
                currentBox.place(boxStagingArea);
                currentBox.setVisible(true);

            }

            //theCamera.lookAt(boxStagingArea);


    }

    private void moveStagedBoxIntoPosition(){
        if(currentBox != null){
            advanceInProgress = true;
            currentBox.moveToLocationOverDuration(Duration.ofSeconds(5), currentBox.getDestination());


        }
    }

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color


        GLES20.glEnable(GLES20.GL_DEPTH_TEST);


        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        theWorld = new World();
        theCamera = new Camera();

        theLoadPlan = TestingLoadPlanGenerator.GenerateBasicSampleLoadPlan(theWorld);

        //theCamera.placeCamera(new Vector(-3f*12f, 8f*12f, -3f*12f));


        boxStagingArea = new Vector(0f, 0f, 0f);

       putNextBoxIntoStaging();





//        Truck t = new Truck(theWorld);
//        t.move(new Vector(3f, 0f, 3f));
//
//        Box test = new Box(24f,24f,24f, theWorld); //a 2 foot box
//        test.place(new Vector(t.getWidthInches() - 24f, 0f, t.getLengthInches() - 24f));
//
//         test2= new Box(48f,48f,24f, theWorld); //a 2 foot box
//        //test2.place(new Vector(32f, 0f, 32f));
//        test2.place(new Vector(0f, 0f, 0f));
//
//
//
//        Sign testSign = new Sign(theWorld, 12f, 12f);
//        //testSign.setMessage("Hello cruel world");
//        testSign.testBitmap(testBitmap);
//        testSign.place(new Vector(1f,1f, 1f));

       // theCamera.focusOn(test2.getOffset(), 2f * 12f);  //focus at the base of the sign

        //theCamera.placeCamera(new Vector(1f, 2f, -2f));
        //theCamera.lookAt(new Vector(1f, 1f, 1f));  //test looking at the truck right corner
    }

    public void onDrawFrame(GL10 unused) {

        theWorld.updateTicks();

        for(Animation a : theWorld.getAnimiations().toArray(Animation[]::new)){
            if(!a.isComplete())
                a.performOperationPerTick();
            else{
                theWorld.removeAnimation(a);
                advanceInProgress = false;
            }

         }

        if(currentBox != null){
            theCamera.placeCamera(currentBox.getOffset().add(new Vector(-3f*12f, 3f*12f, -3f*12f )));
            theCamera.lookAt(currentBox.getOffset());  //always look at the current box
        }




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
            if(wo.isVisible())
                wo.draw(vPMatrix, viewMatrix);
        }




    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1f, 1f, 1f, 100f);



    }





}
