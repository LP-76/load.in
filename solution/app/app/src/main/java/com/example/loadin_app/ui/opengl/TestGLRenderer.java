package com.example.loadin_app.ui.opengl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

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

    private final float[] orthoMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private  float[] viewMatrix = new float[16];
    private float[] hudViewMatrix = new float[16];

    public volatile float angle;



    public Camera getTheCamera(){
        return theCamera;
    }

    private World theWorld;
    private Camera theCamera;
    private Bitmap testBitmap;
    private Hud theHud;

    private LoadPlan theLoadPlan;

    private Vector boxStagingArea;

    private Box currentBox;
    private  int step;
    private boolean advanceInProgress;
    private Context context;

    private Box largeBox;

    public TestGLRenderer(Bitmap source, Context ctx){
        testBitmap = source;
        step = 0;
        advanceInProgress = false;
        context = ctx;
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
            currentBox.moveToLocationOverDuration(Duration.ofSeconds(2), currentBox.getDestination());


        }
    }

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color


        GLES20.glEnable(GLES20.GL_DEPTH_TEST);




        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        theWorld = new World(context);
        theHud = new Hud(theWorld); //setup the hud
        theCamera = new Camera();

        theLoadPlan = TestingLoadPlanGenerator.GenerateBasicSampleLoadPlan(theWorld);

        //theCamera.placeCamera(new Vector(-3f*12f, 8f*12f, -3f*12f));



        boxStagingArea = new Vector(0f, 0f, 0f);

       putNextBoxIntoStaging();

    }

    public void onDrawFrame(GL10 unused) {

        theWorld.updateTicks();

        prepareWorld();
        renderWorld();

        prepareHud();
        renderHud();




    }
    private void prepareWorld(){
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);  //depth test enabled
        GLES20.glDisable(GLES20.GL_BLEND);

    }
    private void prepareHud(){

        GLES20.glDisable(GLES20.GL_DEPTH_TEST);  //we don't depth test for hud
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glEnable(GLES20.GL_BLEND);
        //GLES20.glBlendColor(0f, 0f, 0f, 1f);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

    }
    private void renderWorld(){
        for(Animation a : theWorld.getAnimiations().toArray(Animation[]::new)){
            if(!a.isComplete())
                a.performOperationPerTick();
            else{
                theWorld.removeAnimation(a);
                advanceInProgress = false;
            }

        }

        if(currentBox != null){

            Vector boxCenter = currentBox.getCenter();
            Vector pointOfView = boxCenter.add(new Vector(-3f*12, 2f*12f, -3f*12f));

            theCamera.placeCamera(pointOfView);
            theCamera.lookAt(boxCenter);  //always look at the current box
        }




        // Redraw background color
        GLES20.glClearColor(109f/255f, 209f/255f, 161f/255f, 1f);

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT);


        viewMatrix = theCamera.getLookatMatrix();


        for(WorldObject wo: theWorld.getWorldObjects()){
            if(wo.isVisible())
                wo.draw(theWorld, viewMatrix, projectionMatrix);
        }
    }
    private void renderHud(){

        //theHud.setMessage(LocalDateTime.now().toString());

        theHud.draw(theWorld, hudViewMatrix, orthoMatrix);

    }


    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1f, 1f, 1f, 100f);

        Matrix.orthoM(orthoMatrix, 0, -ratio, ratio, -1f, 1f, 0.1f, 100f );  //close field of vision

        Matrix.setLookAtM(hudViewMatrix,0, 0f, 0f, -0.2f, 0f, 0f, 0f, 0f, 1f, 0f );


    }





}
